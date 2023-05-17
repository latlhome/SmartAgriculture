package com.smart.agriculture.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.DiseaseMenu;
import com.smart.agriculture.Dto.DiseaseMenu.AddCategoryDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.mapper.DiseaseMenuMapper;
import com.smart.agriculture.service.IDiseaseMenuService;
import org.springframework.stereotype.Service;

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

    @Override
    public CommonResult addCategory(AddCategoryDto dto) {
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
        diseaseMenu.setCategory("0");
        int insert = baseMapper.insert(diseaseMenu);
        if (insert > 0) return CommonResult.success("更新成功！");
        else return CommonResult.success("更新失败！");
    }
}
