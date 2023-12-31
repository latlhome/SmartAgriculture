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
import com.smart.agriculture.Dto.DiseaseMenu.*;
import com.smart.agriculture.Dto.PageDto;
import com.smart.agriculture.Vo.DiseaseMenu.GetCategoryVo;
import com.smart.agriculture.Vo.DiseaseMenu.GetPlantsByCategoryIdVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.enums.DiseaseMenu.MenuType;
import com.smart.agriculture.mapper.DiseaseMenuMapper;
import com.smart.agriculture.mapper.PlantDiseaseMapper;
import com.smart.agriculture.service.IDiseaseMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 病害菜单 服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class DiseaseMenuServiceImpl extends ServiceImpl<DiseaseMenuMapper, DiseaseMenu> implements IDiseaseMenuService {
    @Resource
    private PlantDiseaseMapper diseaseMapper;

    @Override
    public CommonResult<String> addCategory(AddCategoryDto dto) {
        //进行判断类别是否存在
        List<DiseaseMenu> diseaseMenus = baseMapper.selectList(new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getMenuType,0));
        for (DiseaseMenu diseaseMenu : diseaseMenus) {
            if (diseaseMenu.getName().equals(dto.getCategoryName()))
                return CommonResult.failed("已有该类别！");
        }
        //不存在进行新增操作
        DiseaseMenu diseaseMenu = new DiseaseMenu();
        diseaseMenu.setName(dto.getCategoryName());
        diseaseMenu.setMenuSamplePicture(dto.getMenuSamplePicture());
        diseaseMenu.setMenuType(0);
        diseaseMenu.setCategoryId("0");
        int insert = baseMapper.insert(diseaseMenu);
        if (insert > 0) return CommonResult.success("新增成功！");
        else return CommonResult.success("新增失败！");
    }

    @Override
    public CommonResult<String> addPlant(AddPlantDto dto) {
        //进行判断类别是否存在
        List<DiseaseMenu> diseaseMenus = baseMapper.selectList(new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getMenuType,dto.getCategoryId()));
        for (DiseaseMenu diseaseMenu : diseaseMenus) {
            if (diseaseMenu.getName().equals(dto.getCategoryName()))
                return CommonResult.failed("该类别下已有该植物！");
        }
        //不存在进行新增操作
        DiseaseMenu diseaseMenu = new DiseaseMenu();
        diseaseMenu.setName(dto.getCategoryName());
        diseaseMenu.setMenuSamplePicture(dto.getMenuSamplePicture());
        diseaseMenu.setMenuType(1);
        diseaseMenu.setCategoryId(dto.getCategoryId());
        int insert = baseMapper.insert(diseaseMenu);
        if (insert > 0) return CommonResult.success("新增成功！");
        else return CommonResult.success("新增失败！");
    }

    @Override
    public CommonResult<String> updateCategory(UpdateCategoryDto dto) {
        DiseaseMenu diseaseMenu = baseMapper.selectOne(new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getMenuType, MenuType.CATEGORY.getCode()).eq(DiseaseMenu::getId, dto.getId()));
        if (ObjectUtil.isNull(diseaseMenu)) return CommonResult.failed("更新的类别不存在！");
        DiseaseMenu newDiseaseMenu = new DiseaseMenu();
        BeanUtil.copyProperties(dto,newDiseaseMenu);
        int i = baseMapper.updateById(newDiseaseMenu);
        if (i>0) return CommonResult.success("更新成功！");
        else return CommonResult.failed("更新出错！");
    }

    @Override
    public CommonResult<String> deleteCategory(String id) {
        DiseaseMenu diseaseMenu = baseMapper.selectOne(new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getMenuType, MenuType.CATEGORY.getCode()).eq(DiseaseMenu::getId,id));
        if (ObjectUtil.isNull(diseaseMenu)) return CommonResult.failed("要删除的类别不存在！");
        List<DiseaseMenu> diseaseMenus = baseMapper.selectList(new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getMenuType, MenuType.PLANT.getCode()).eq(DiseaseMenu::getCategoryId,id));
        if (diseaseMenus.size()>0) return CommonResult.failed("类别下还有植物，无法删除！");
        int i = baseMapper.deleteById(id);
        if (i>0) return CommonResult.success("删除成功!");
        else return CommonResult.failed("删除出错！");
    }

    @Override
    public CommonResult<List<GetCategoryVo>> getCategory(PageDto pageDto) {
        List<GetCategoryVo> vo = new ArrayList<>();
        LambdaQueryWrapper<DiseaseMenu> lambda = new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getCategoryId, MenuType.CATEGORY.getCode());
        IPage<DiseaseMenu> diseaseMenus = baseMapper.selectPage(new Page<>(pageDto.getPageNum(), pageDto.getPageSize()), lambda);
        for (DiseaseMenu diseaseMenu : diseaseMenus.getRecords()) {
            GetCategoryVo getCategoryVo = new GetCategoryVo();
            BeanUtil.copyProperties(diseaseMenu,getCategoryVo);
            vo.add(getCategoryVo);
        }
        return CommonResult.success(vo);
    }

    @Override
    public CommonResult<String> updatePlant(UpdatePlantDto dto) {
        Integer integer = baseMapper.selectPlantById(dto.getId());
        if (integer == 0) return CommonResult.failed("更新的植物不存在！");
        DiseaseMenu newDiseaseMenu = new DiseaseMenu();
        BeanUtil.copyProperties(dto,newDiseaseMenu);
        int i = baseMapper.updateById(newDiseaseMenu);
        if (i>0) return CommonResult.success("更新成功！");
        else return CommonResult.failed("更新出错！");
    }

    @Override
    public CommonResult<String> deletePlant(String id) {
        Integer integer = baseMapper.selectPlantById(id);
        if (integer == 0) return CommonResult.failed("要删除的植物不存在！");
        List<PlantDisease> plantDiseases = diseaseMapper.selectList(new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getPlantId, id));
        if (ObjectUtil.isNotNull(plantDiseases)) return CommonResult.failed("该植物下还有相关病害，请确保全部删除之后删除植物！");
        int i = baseMapper.deleteById(id);
        if (i>0) return CommonResult.success("删除成功!");
        else return CommonResult.failed("删除出错！");
    }

    @Override
    public CommonResult<List<GetPlantsByCategoryIdVo>> getPlantsByCategoryId(ByIdPage page) {
        List<GetPlantsByCategoryIdVo> vo = new ArrayList<>();
        DiseaseMenu diseaseMenu = baseMapper.selectOne(new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getMenuType, MenuType.CATEGORY.getCode()).eq(DiseaseMenu::getId, page.getId()));
        if (ObjectUtil.isNull(diseaseMenu)) return CommonResult.failed("查找的类别不存在！");
        LambdaQueryWrapper<DiseaseMenu> lambda = new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getMenuType, MenuType.PLANT.getCode()).eq(DiseaseMenu::getCategoryId, page.getId());
        IPage<DiseaseMenu> diseaseMenus = baseMapper.selectPage(new Page<>(page.getPageNum(), page.getPageSize()),lambda);
        for (DiseaseMenu menu : diseaseMenus.getRecords()) {
            GetPlantsByCategoryIdVo getPlantsByCategoryIdVo = new GetPlantsByCategoryIdVo();
            BeanUtil.copyProperties(menu,getPlantsByCategoryIdVo);
            vo.add(getPlantsByCategoryIdVo);
        }
        return CommonResult.success(vo);
    }

    @Override
    public CommonResult<String> plantToOtherCategory(PlantToOtherCategoryDto dto) {
        DiseaseMenu diseaseMenu = baseMapper.selectOne(new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getMenuType, MenuType.PLANT.getCode()).eq(DiseaseMenu::getId,dto.getPlantId()));
        if (ObjectUtil.isNull(diseaseMenu)) return CommonResult.failed("要移动的植物不存在!");
        DiseaseMenu diseaseMenuCategory = baseMapper.selectOne(new QueryWrapper<DiseaseMenu>().lambda().eq(DiseaseMenu::getMenuType, MenuType.CATEGORY.getCode()).eq(DiseaseMenu::getId,dto.getCategoryId()));
        if (ObjectUtil.isNull(diseaseMenuCategory)) return CommonResult.failed("目标类别不存在！");
        DiseaseMenu flag = new DiseaseMenu();
        BeanUtil.copyProperties(diseaseMenu,flag);
        flag.setCategoryId(dto.getCategoryId());
        int i = baseMapper.updateById(flag);
        if (i>0) return CommonResult.success("更新成功!");
        else return CommonResult.failed("更新出错！");
    }
}
