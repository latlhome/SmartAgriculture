package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.FreedomArticle;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Dto.FreedomArticle.AddFreedomArticleDto;
import com.smart.agriculture.Dto.FreedomArticle.QueryOfFollowDto;
import com.smart.agriculture.Dto.FreedomArticle.SelectFreedomArticleListDto;
import com.smart.agriculture.Vo.FreedomArticle.ScrollResultVo;
import com.smart.agriculture.Vo.FreedomArticle.SelectFreedomArticleListVo;
import com.smart.agriculture.Vo.FreedomArticle.SelectFreedomArticleVo;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.common.utils.JwtTokenUtil;
import com.smart.agriculture.enums.SysUser.UserType;
import com.smart.agriculture.mapper.*;
import com.smart.agriculture.service.IFreedomArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.smart.agriculture.common.config.RedisConstants.BLOG_LIKED_KEY;
import static com.smart.agriculture.common.config.RedisConstants.FEED_KEY;

/**
 * <p>
 *  自由论坛帖子服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class FreedomArticleServiceImpl extends ServiceImpl<FreedomArticleMapper, FreedomArticle> implements IFreedomArticleService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private DiseaseMenuMapper diseaseMenuMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserFollowMapper userFollowMapper;
    @Resource
    private HttpServletRequest httpServletRequest;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public CommonResult<String> addFreedomArticle(AddFreedomArticleDto addFreedomArticleDto) {
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        SysUser sysUser = sysUserMapper.selectOneByUsername(username);
        if (ObjectUtil.isNull(sysUser)) return CommonResult.failed("发布用户不存在！");
        Integer integer = diseaseMenuMapper.selectPlantById(addFreedomArticleDto.getPlantId());
        if (integer == 0) return CommonResult.failed("对应农作物不存在！");
        FreedomArticle freedomArticle = new FreedomArticle();
        BeanUtil.copyProperties(addFreedomArticleDto,freedomArticle);
        freedomArticle.setAuthorUsername(username);
        int i = baseMapper.insert(freedomArticle);
        if (i>0){
            List<String> list = userFollowMapper.selectVermicelliList(username);
            // 推送笔记id给所有粉丝
            for (String followUsername : list) {
                // 推送
                String key = FEED_KEY + followUsername;
                stringRedisTemplate.opsForZSet().add(key, freedomArticle.getId().toString(), System.currentTimeMillis());
            }
            return CommonResult.success("创建成功！");
        }
        else return CommonResult.failed("创建出错!");
    }

    @Override
    public CommonResult<PageVo<SelectFreedomArticleListVo>> selectFreedomArticleList(SelectFreedomArticleListDto dto) {
        PageVo<SelectFreedomArticleListVo> pagevo = new PageVo<>();
        List<SelectFreedomArticleListVo> vo = new ArrayList<>();
        LambdaQueryWrapper<FreedomArticle> lambda = new QueryWrapper<FreedomArticle>().lambda();
        if (ObjectUtil.isNotNull(dto.getPlantId())) {
            Integer integer = diseaseMenuMapper.selectPlantById(dto.getPlantId());
            if (integer == 0) return CommonResult.failed("对应农作物不存在！");
            lambda.eq(FreedomArticle::getPlantId,dto.getPlantId());
        }
        if (ObjectUtil.isNotNull(dto.getLike())) lambda.like(FreedomArticle::getTitle,dto.getLike());
        IPage<FreedomArticle> freedomArticleIPage = baseMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), lambda);
        for (FreedomArticle record : freedomArticleIPage.getRecords()) {
            SelectFreedomArticleListVo flag = new SelectFreedomArticleListVo();
            BeanUtil.copyProperties(record,flag);
            flag.setDrawing(Arrays.asList(record.getDrawing().split("#")));
            vo.add(flag);
        }
        pagevo.setData(vo);
        pagevo.setTotalSize(freedomArticleIPage.getTotal());
        pagevo.setPageSize(freedomArticleIPage.getSize());
        pagevo.setPageNum(freedomArticleIPage.getCurrent());
        pagevo.setTotalPages(freedomArticleIPage.getPages());
        return CommonResult.success(pagevo);
    }

    @Override
    public CommonResult<SelectFreedomArticleVo> selectFreedomArticleById(String id) {
        SelectFreedomArticleVo vo = new SelectFreedomArticleVo();
        FreedomArticle freedomArticle = baseMapper.selectArticleById(id);
        BeanUtil.copyProperties(freedomArticle,vo);
        vo.setDrawings(Arrays.asList(freedomArticle.getDrawing().split("#")));
        SysUser sysUser = sysUserMapper.selectOneByUsername(freedomArticle.getAuthorUsername());
        SysUserArticleVo sysUserArticleVo = new SysUserArticleVo();
        sysUserArticleVo.setAuthorUsername(sysUser.getUsername());
        sysUserArticleVo.setAuthorNickname(sysUser.getNickname());
        sysUserArticleVo.setAuthorPicture(sysUser.getHeadPicture());
        vo.setUserArticleVo(sysUserArticleVo);
        //是否点赞
        isBlogLiked(vo);
        //TODO
        vo.setIsCollect(null);
        return CommonResult.success(vo);
    }

    @Override
    @Transactional
    public CommonResult<String> deleteFreedomArticle(String id) {
        FreedomArticle freedomArticle = baseMapper.selectArticleById(id);
        if (ObjectUtil.isNull(freedomArticle)) return CommonResult.failed("该帖子不存在");
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        SysUser sysUser = sysUserMapper.selectOneByUsername(username);
        if (!Objects.equals(sysUser.getUserType(), UserType.developer.getCode()) && !Objects.equals(username, freedomArticle.getAuthorUsername()))
            return CommonResult.failed("您没有权限删除此贴！");
        int i = baseMapper.deleteById(id);
        if (i>0) {
            //查询这人的粉丝
            List<String> strings = userFollowMapper.selectVermicelliList(username);
            for (String string : strings) {
                String key = FEED_KEY + string;
                stringRedisTemplate.opsForZSet().remove(key,id);
            }
            return CommonResult.success("删除成功！");
        }
        else return CommonResult.failed("删除失败！");
    }

    @Override
    public CommonResult<String> likeFreedomArticle(String id) {
        FreedomArticle freedomArticle = baseMapper.selectArticleById(id);
        if (ObjectUtil.isNull(freedomArticle)) return  CommonResult.failed("帖子不存在");
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        // 判断当前登录用户是否已经点赞
        String key = BLOG_LIKED_KEY + id;
        Double score = stringRedisTemplate.opsForZSet().score(key, username);
        if (score == null) {
            // 如果未点赞，可以点赞
            // 数据库点赞数 + 1
            boolean isSuccess = update().setSql("liked = liked + 1").eq("id", id).update();
            // 保存用户到Redis的set集合  zadd key value score
            if (isSuccess) {
                stringRedisTemplate.opsForZSet().add(key,username, System.currentTimeMillis());
            }
            return CommonResult.success("点赞成功！");
        } else {
            // 4.如果已点赞，取消点赞
            // 4.1.数据库点赞数 -1
            boolean isSuccess = update().setSql("liked = liked - 1").eq("id", id).update();
            // 4.2.把用户从Redis的set集合移除
            if (isSuccess) {
                stringRedisTemplate.opsForZSet().remove(key,username);
            }
            return CommonResult.success("操作成功！");
        }

    }

    @Override
    public CommonResult<ScrollResultVo> queryOfFollow(QueryOfFollowDto dto) {
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        if (StringUtils.isBlank(username)) return CommonResult.failed("无登录用户");
        // 查询收件箱
        String key = FEED_KEY + username;
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.opsForZSet()
                .reverseRangeByScoreWithScores(key, 0, dto.getMax(), dto.getOffset(), dto.getCount());
        // 非空判断
        if (typedTuples == null || typedTuples.isEmpty()) {
            return CommonResult.success();
        }
        // 解析数据：ID、minTime（时间戳）、offset
        List<Long> ids = new ArrayList<>(typedTuples.size());
        long minTime = 0;
        int os = 1;
        for (ZSetOperations.TypedTuple<String> tuple : typedTuples) {
            // 4.1.获取id
            ids.add(Long.valueOf(Objects.requireNonNull(tuple.getValue())));
            // 4.2.获取分数(时间戳）
            long time = Objects.requireNonNull(tuple.getScore()).longValue();
            if(time == minTime){
                os++;
            }else{
                minTime = time;
                os = 1;
            }
        }
        List<FreedomArticle> freedomArticles = baseMapper.selectList(new QueryWrapper<FreedomArticle>().lambda().in(FreedomArticle::getId, ids).orderByDesc(FreedomArticle::getCreateTime));
        List<SelectFreedomArticleListVo> vo = new ArrayList<>();
        for (FreedomArticle freedomArticle : freedomArticles) {
            SelectFreedomArticleListVo selectFreedomArticleListVo = new SelectFreedomArticleListVo();
            BeanUtil.copyProperties(freedomArticle,selectFreedomArticleListVo);
            vo.add(selectFreedomArticleListVo);
        }

        ScrollResultVo scrollResultVo = new ScrollResultVo();
        scrollResultVo.setList(vo);
        scrollResultVo.setMinTime(minTime);
        scrollResultVo.setOffset(os);
        return CommonResult.success(scrollResultVo);
    }

    public void deleteArticleAllComment(String id){
        try {
            List<String> d = commentMapper.selectArticleAllComment(id);
            for (String s : d) {
                List<String> strings = commentMapper.selectArticleAllComment(s);
                strings.add(s);
                commentMapper.deleteBatchIds(strings);
            }
        } catch (Exception e) {
            throw new RuntimeException("操作出错，请联系管理员或反馈问题！");
        }
    }
    private void isBlogLiked(SelectFreedomArticleVo article) {
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        if (StringUtils.isBlank(username)) {
            // 用户未登录，无需查询是否点赞
            return;
        }
        // 2.判断当前登录用户是否已经点赞
        String key = BLOG_LIKED_KEY + article.getId();
        Double score = stringRedisTemplate.opsForZSet().score(key,username);
        article.setIsLike(score != null);
    }
}
