package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.PlantDisease;
import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Dto.PlantDisease.AddPlantDiseaseDto;
import com.smart.agriculture.Dto.PlantDisease.UpdatePlantDiseaseDto;
import com.smart.agriculture.Vo.PlantDisease.GetPlantDiseaseByIdVo;
import com.smart.agriculture.Vo.PlantDisease.SelectPlantDiseaseVo;
import com.smart.agriculture.common.config.RedisConstants;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.mapper.DiseaseMenuMapper;
import com.smart.agriculture.mapper.PlantDiseaseMapper;
import com.smart.agriculture.service.IIsVoidService;
import com.smart.agriculture.service.IPlantDiseaseService;
import com.visual.disease.core.domain.Output;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.smart.agriculture.common.config.RedisConstants.CACHE_ARTICLE_TTL;
import static com.smart.agriculture.common.config.RedisConstants.CACHE_NULL_TTL;

/**
 * <p>
 * 植物病害 服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class PlantDiseaseServiceImpl extends ServiceImpl<PlantDiseaseMapper, PlantDisease> implements IPlantDiseaseService {
    @Resource
    private DiseaseMenuMapper diseaseMenuMapper;
    @Resource
    private IIsVoidService isVoidService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public CommonResult<String> addPlantDisease(AddPlantDiseaseDto dto) {
        //判断传输过来的植物种类是否有问题
        Integer integer = diseaseMenuMapper.selectPlantById(dto.getPlantId());
        if (integer == 0)   return CommonResult.failed("该植物种类不存在");
        //进行判断类别是否存在
        List<PlantDisease> plantDiseases = baseMapper.selectList(new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getPlantId,dto.getPlantId()));
        for (PlantDisease plantDisease : plantDiseases) {
            if (plantDisease.getDiseaseName().equals(dto.getDiseaseName()))
                return CommonResult.failed("病害数据库中已有该植物的该病种");
        }
        //不存在进行新增操作
        PlantDisease plantDisease = new PlantDisease();
        BeanUtil.copyProperties(dto,plantDisease);
        int insert = baseMapper.insert(plantDisease);
        if (insert > 0) return CommonResult.success("新增成功！");
        else return CommonResult.success("新增失败！");
    }

    @Override
    public CommonResult<String> updatePlantDisease(UpdatePlantDiseaseDto dto) {
        //判断传输过来的植物种类是否有问题
        Integer count = diseaseMenuMapper.selectPlantById(dto.getPlantId());
        if (count == 0)   return CommonResult.failed("该植物种类不存在");
        Integer integer = baseMapper.selectCount(new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getDiseaseName, dto.getDiseaseName()).eq(PlantDisease::getPlantId, dto.getPlantId()).ne(PlantDisease::getId,dto.getId()));
        if (integer>0) return CommonResult.failed("该植物下已有对应名称的病害！");
        PlantDisease plantDisease = new PlantDisease();
        BeanUtil.copyProperties(dto,plantDisease);
        int i = baseMapper.updateById(plantDisease);
        if (i > 0){
            String key = RedisConstants.CACHE_DISEASE_KEY + dto.getId();
            stringRedisTemplate.delete(key);
            return CommonResult.success("更新成功！");
        }
        return CommonResult.success("更新出错！");
    }

    @Override
    public CommonResult<String> deletePlantDisease(String id) {
        PlantDisease plantDisease = baseMapper.selectOne(new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getId, id));
        if (ObjectUtil.isNull(plantDisease)) return CommonResult.failed("该病害不存在！");
        int i = baseMapper.deleteById(id);
        if (i > 0) {
            String key = RedisConstants.CACHE_DISEASE_KEY + id;
            stringRedisTemplate.delete(key);
            return CommonResult.success("删除成功！");
        }
        else return CommonResult.success("删除出错！");
    }

    @Override
    public CommonResult<List<GetPlantDiseaseByIdVo>> getPlantDiseasesById(ByIdPage page) {
        List<GetPlantDiseaseByIdVo> vo = new ArrayList<>();
        Integer integer = diseaseMenuMapper.selectPlantById(page.getId());
        if (integer == 0)   return CommonResult.failed("该植物种类不存在");
        LambdaQueryWrapper<PlantDisease> lambda = new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getPlantId, page.getId());
        IPage<PlantDisease> plantDiseaseIPage = baseMapper.selectPage(new Page<>(page.getPageNum(), page.getPageSize()), lambda);
        for (PlantDisease record : plantDiseaseIPage.getRecords()) {
            GetPlantDiseaseByIdVo getPlantDiseaseByIdVo = new GetPlantDiseaseByIdVo();
            BeanUtil.copyProperties(record,getPlantDiseaseByIdVo);
            vo.add(getPlantDiseaseByIdVo);
        }
        return CommonResult.success(vo);
    }

    @Override
    public CommonResult<SelectPlantDiseaseVo> selectPlantDiseaseById(String id) {
        return CommonResult.success(queryWithPassThrough(id));
    }

    @Override
    public List<Output> SecondaryProcessing(List<Output> outputList) {
        for (Output output : outputList) {
            PlantDisease plantDisease = baseMapper.selectOne(new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getDiseaseName, output.getPostprocess()));
            if (ObjectUtil.isNotNull(plantDisease)){
                output.setId(String.valueOf(plantDisease.getId()));
                output.setSamplePicture(Arrays.asList(plantDisease.getSamplePicture().split("#")).get(0));
                output.setIntroduction(plantDisease.getIntroduction());
            }
        }
        return outputList;
    }
    private SelectPlantDiseaseVo queryWithPassThrough(String id) {
        String key = RedisConstants.CACHE_DISEASE_KEY + id;
        // 1.从redis查询帖子缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2.判断是否存在
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toBean(json, SelectPlantDiseaseVo.class);
        }
        // 判断命中的是否是空值
        if (json != null) {
            return null;
        }
        // 4.不存在，根据id查询数据库
        PlantDisease disease = baseMapper.selectOne(new QueryWrapper<PlantDisease>().lambda()
                .eq(PlantDisease::getId,id));
        // 5.不存在，返回错误
        if (disease == null) {
            // 将空值写入redis
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 返回错误信息
            return null;
        }
        SelectPlantDiseaseVo vo = new SelectPlantDiseaseVo();
        BeanUtil.copyProperties(disease,vo);
        vo.setSamplePicture(Arrays.asList(disease.getSamplePicture().split("#")));
        // 6.存在，写入redis
        this.set(key, vo, CACHE_ARTICLE_TTL, TimeUnit.MINUTES);
        return vo;
    }
    public void set(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }
}
