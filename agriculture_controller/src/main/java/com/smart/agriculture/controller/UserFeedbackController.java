package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.UserFeedback.AddUserFeedbackDto;
import com.smart.agriculture.Dto.UserFeedback.UpdateUserFeedbackDto;
import com.smart.agriculture.Dto.UserFeedback.UserFeedbackListDto;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.Vo.UserFeedback.UserFeedbackListVo;
import com.smart.agriculture.Vo.UserFeedback.UserFeedbackVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IUserFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ylx
 * @since 2023-05-22
 */
@RestController
@RequestMapping("/userFeedback")
@Api(tags = "用户反馈")
public class UserFeedbackController {
    @Resource
    private IUserFeedbackService baseService;

    @PostMapping("/add")
    @ApiOperation("用户新建反馈")
    public CommonResult<String> addUserFeedback(@RequestBody AddUserFeedbackDto dto){
        return baseService.addUserFeedback(dto);
    }
    @GetMapping("/getList")
    @ApiOperation("查看所有用户反馈")
    public CommonResult<PageVo<UserFeedbackListVo>> getUserFeedbackList(UserFeedbackListDto dto){
        return baseService.getUserFeedbackList(dto);
    }

    @GetMapping("/getList/{id}")
    @ApiOperation("查看反馈详细")
    public CommonResult<UserFeedbackVo> getUserFeedback(@PathVariable("id") Long id){
        return baseService.getUserFeedback(id);
    }
    @GetMapping("/updateUserFeedback")
    @ApiOperation("管理员更新反馈状态")
    public CommonResult<String> updateUserFeedback(UpdateUserFeedbackDto dto){
        return baseService.updateUserFeedback(dto);
    }

}

