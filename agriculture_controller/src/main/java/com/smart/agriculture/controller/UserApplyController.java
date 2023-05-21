package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.UserApply.AddUserApplyDto;
import com.smart.agriculture.Dto.UserApply.UpdateUserApplyDto;
import com.smart.agriculture.Vo.UserApply.ApplyDetailsVo;
import com.smart.agriculture.Vo.UserApply.MyApplyListVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IUserApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ylx
 * @since 2023-05-21
 */
@RestController
@RequestMapping("/userApply")
@Api(tags = "用户申请身份")
public class UserApplyController {
    @Resource
    private IUserApplyService baseService;
    @PostMapping("/add")
    @ApiOperation("用户申请身份")
    public CommonResult<String> addUserApply(@RequestBody AddUserApplyDto dto){
        return baseService.addUserApply(dto);
    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除申请")
    public CommonResult<String> deleteUserApply(@PathVariable("id") String id){
        return baseService.deleteUserApply(id);
    }
    @PutMapping("/update")
    @ApiOperation("更新申请")
    public CommonResult<String> updateUserApply(UpdateUserApplyDto dto){
        return baseService.updateUserApply(dto);
    }
    @GetMapping("/getMyApply")
    @ApiOperation("获取自己申请List")
    public CommonResult<List<MyApplyListVo>> getMyApplyList(){
        return baseService.getMyApplyList();
    }

    @GetMapping("/getApplyOne/{id}")
    @ApiOperation("查看自己申请详细")
    public CommonResult<ApplyDetailsVo> getApplyOne(@PathVariable("id") String id){
        return baseService.getApplyOne(id);
    }
    @GetMapping("/getAllApply")
    @ApiOperation("获取所有人申请详细")
    public CommonResult<List<MyApplyListVo>> getMyApplyOne(){
        return baseService.getAllApplyList();
    }
}

