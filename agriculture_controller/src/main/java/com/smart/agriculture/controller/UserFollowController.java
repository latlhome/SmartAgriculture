package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.UserFollow.IsFollowDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IUserFollowService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ylx
 * @since 2023-05-19
 */
@RestController
@RequestMapping("/userFollow")
public class UserFollowController {
    @Resource
    private IUserFollowService followService;
    @PutMapping("/Follow")
    public CommonResult<String> follow(IsFollowDto isFollow) {
        return followService.follow(isFollow);
    }

}

