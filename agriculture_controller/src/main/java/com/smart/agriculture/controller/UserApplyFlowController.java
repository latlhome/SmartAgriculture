package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.UserApplyFlow.ApproveDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IUserApplyFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ylx
 * @since 2023-05-21
 */
@RestController
@RequestMapping("/userApplyFlow")
@Api(tags = "审批一下用户的身份转换")
public class UserApplyFlowController {
    @Resource
    private IUserApplyFlowService baseService;
    @PostMapping("/approve")
    @ApiOperation("审批")
    public CommonResult<String> approve(@RequestBody ApproveDto dto){
        return baseService.addUserApply(dto);
    }
}

