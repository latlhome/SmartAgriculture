package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.PlantingLog;
import com.smart.agriculture.Dto.PlantingLog.AddPlantingLogDto;
import com.smart.agriculture.Dto.PlantingLog.UpdatePlantingLogDto;
import com.smart.agriculture.Vo.PlantingLog.GetPlantingLogListVo;
import com.smart.agriculture.Vo.PlantingLog.GetPlantingLogVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.common.utils.JwtTokenUtil;
import com.smart.agriculture.mapper.PlantingLogMapper;
import com.smart.agriculture.service.IPlantingLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  种植日志服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class PlantingLogServiceImpl extends ServiceImpl<PlantingLogMapper, PlantingLog> implements IPlantingLogService {
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private HttpServletRequest httpServletRequest;


    @Override
    public CommonResult<String> addPlantingLog(AddPlantingLogDto dto) {
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        if (StringUtils.isBlank(username)) return CommonResult.failed("登陆后才能操作！");
        PlantingLog plantingLog = new PlantingLog();
        BeanUtil.copyProperties(dto,plantingLog);
        plantingLog.setAuthorUsername(username);
        int insert = baseMapper.insert(plantingLog);
        if (insert>0) return CommonResult.success("新增成功！");
        return CommonResult.failed("新增失败！");
    }

    @Override
    public CommonResult<String> updatePlantingLog(UpdatePlantingLogDto dto) {
        PlantingLog plantingLog = baseMapper.selectPlantingLogById(dto.getId());
        if (ObjectUtil.isNull(plantingLog)) return CommonResult.failed("日志不存在！");
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        if (!Objects.equals(plantingLog.getAuthorUsername(), username)) return CommonResult.failed("您没有权限修改此日志！");
        PlantingLog flag = new PlantingLog();
        BeanUtil.copyProperties(dto,flag);
        int i = baseMapper.updateById(flag);
        if (i>0) return CommonResult.success("更新成功！");
        return CommonResult.failed("更新失败！");
    }

    @Override
    public CommonResult<String> deletePlantingLog(Long id) {
        PlantingLog plantingLog = baseMapper.selectPlantingLogById(id);
        if (ObjectUtil.isNull(plantingLog)) return CommonResult.failed("日志不存在！");
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        if (!Objects.equals(plantingLog.getAuthorUsername(), username)) return CommonResult.failed("您没有权限删除此日志！");
        int i = baseMapper.deleteById(id);
        if (i>0) return CommonResult.success("删除成功！");
        return CommonResult.failed("删除失败！");
    }

    @Override
    public CommonResult<List<GetPlantingLogListVo>> getPlantingLogList() {
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        if (StringUtils.isBlank(username)) return CommonResult.failed("登陆后再操作！");
        List<PlantingLog> plantingLogs = baseMapper.selectList(new QueryWrapper<PlantingLog>().lambda()
                .eq(PlantingLog::getAuthorUsername, username).orderByDesc(PlantingLog::getCreateTime));
        List<GetPlantingLogListVo> vos = new ArrayList<>();
        for (PlantingLog plantingLog : plantingLogs) {
            GetPlantingLogListVo flag = new GetPlantingLogListVo();
            BeanUtil.copyProperties(plantingLog,flag);
            vos.add(flag);
        }
        return CommonResult.success(vos);
    }

    @Override
    public CommonResult<GetPlantingLogVo> getPlantingLog(Long id) {
        PlantingLog plantingLog = baseMapper.selectPlantingLogById(id);
        if (ObjectUtil.isNull(plantingLog)) return CommonResult.failed("日志不存在！");
        GetPlantingLogVo vo = new GetPlantingLogVo();
        BeanUtil.copyProperties(plantingLog,vo);
        return CommonResult.success(vo);
    }
}
