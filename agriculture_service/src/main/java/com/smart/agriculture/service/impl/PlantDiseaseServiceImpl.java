package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.DiseaseMenu;
import com.smart.agriculture.Do.PlantDisease;
import com.smart.agriculture.Dto.PlantDisease.AddPlantDiseaseDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.enums.DiseaseMenu.MenuType;
import com.smart.agriculture.mapper.DiseaseMenuMapper;
import com.smart.agriculture.mapper.PlantDiseaseMapper;
import com.smart.agriculture.service.IPlantDiseaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public CommonResult addPlantDisease(AddPlantDiseaseDto dto) {
        //判断传输过来的植物种类是否有问题
        DiseaseMenu diseaseMenu = diseaseMenuMapper.selectOne(new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getMenuType, MenuType.PLANT.getCode()).eq(DiseaseMenu::getId, dto.getPlantId()));
        if (ObjectUtil.isNull(diseaseMenu))   return CommonResult.failed("该植物种类不存在");
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
}
