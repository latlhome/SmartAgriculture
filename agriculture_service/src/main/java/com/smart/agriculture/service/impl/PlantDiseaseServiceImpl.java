package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.DiseaseMenu;
import com.smart.agriculture.Do.PlantDisease;
import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Dto.PlantDisease.AddPlantDiseaseDto;
import com.smart.agriculture.Dto.PlantDisease.UpdatePlantDiseaseDto;
import com.smart.agriculture.Vo.PlantDisease.GetPlantDiseaseByIdVo;
import com.smart.agriculture.Vo.PlantDisease.SelectPlantDiseaseVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.enums.DiseaseMenu.MenuType;
import com.smart.agriculture.mapper.DiseaseMenuMapper;
import com.smart.agriculture.mapper.PlantDiseaseMapper;
import com.smart.agriculture.service.IPlantDiseaseService;
import com.visual.disease.core.domain.Output;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Override
    public CommonResult updatePlantDisease(UpdatePlantDiseaseDto dto) {
        //判断传输过来的植物种类是否有问题
        DiseaseMenu diseaseMenu = diseaseMenuMapper.selectOne(new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getMenuType, MenuType.PLANT.getCode()).eq(DiseaseMenu::getId, dto.getPlantId()));
        if (ObjectUtil.isNull(diseaseMenu))   return CommonResult.failed("该植物种类不存在");
        Integer integer = baseMapper.selectCount(new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getDiseaseName, dto.getDiseaseName()).eq(PlantDisease::getPlantId, dto.getPlantId()).ne(PlantDisease::getId,dto.getId()));
        if (integer>0) return CommonResult.failed("该植物下已有对应名称的病害！");
        PlantDisease plantDisease = new PlantDisease();
        BeanUtil.copyProperties(dto,plantDisease);
        int i = baseMapper.updateById(plantDisease);
        if (i > 0) return CommonResult.success("更新成功！");
        else return CommonResult.success("更新出错！");
    }

    @Override
    public CommonResult deletePlantDisease(String id) {
        PlantDisease plantDisease = baseMapper.selectOne(new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getId, id));
        if (ObjectUtil.isNull(plantDisease)) return CommonResult.failed("该病害不存在！");
        int i = baseMapper.deleteById(id);
        if (i > 0) return CommonResult.success("删除成功！");
        else return CommonResult.success("删除出错！");
    }

    @Override
    public CommonResult getPlantDiseasesById(ByIdPage page) {
        List<GetPlantDiseaseByIdVo> vo = new ArrayList<>();
        DiseaseMenu diseaseMenu = diseaseMenuMapper.selectOne(new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getId, page.getId()));
        if (ObjectUtil.isNull(diseaseMenu)) return CommonResult.failed("该植物不存在");
        LambdaQueryWrapper<PlantDisease> lambda = new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getPlantId, page.getId());
        IPage<PlantDisease> plantDiseaseIPage = baseMapper.selectPage(new Page<>(page.getPageNum(), page.getPageSize()), lambda);
        for (PlantDisease record : plantDiseaseIPage.getRecords()) {
            GetPlantDiseaseByIdVo getPlantDiseaseByIdVo = new GetPlantDiseaseByIdVo();
            BeanUtil.copyProperties(getPlantDiseaseByIdVo,record);
            vo.add(getPlantDiseaseByIdVo);
        }
        return CommonResult.success(vo);
    }

    @Override
    public CommonResult selectPlantDiseaseById(String id) {
        PlantDisease plantDisease = baseMapper.selectOne(new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getId, id));
        if (ObjectUtil.isNull(plantDisease)) return CommonResult.failed("相关病害不存在");
        SelectPlantDiseaseVo vo = new SelectPlantDiseaseVo();
        BeanUtil.copyProperties(plantDisease,vo);
        return CommonResult.success(vo);
    }

    @Override
    public List<Output> SecondaryProcessing(List<Output> outputList) {
        for (Output output : outputList) {
            PlantDisease plantDisease = baseMapper.selectOne(new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getDiseaseName, output.getPostprocess()));
            output.setId(String.valueOf(plantDisease.getId()));
            output.setIntroduction(plantDisease.getIntroduction());
        }
        return outputList;
    }
}
