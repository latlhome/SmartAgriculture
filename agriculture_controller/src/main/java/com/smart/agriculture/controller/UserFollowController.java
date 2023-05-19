package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.UserFollow.IsFollowDto;
import com.smart.agriculture.Vo.UserFollow.SelectUserFollowListVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IUserFollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
@Api(tags = "关注信息")
public class UserFollowController {
    @Resource
    private IUserFollowService followService;
    @PutMapping("/follow")
    @ApiOperation("关注 or 取关")
    public CommonResult<String> follow(IsFollowDto isFollow) {
        return followService.follow(isFollow);
    }

    @GetMapping("/followList")
    @ApiOperation("查看自己关注列表")
    public CommonResult<List<SelectUserFollowListVo>> selectFollowList() {
        return followService.selectFollowList();
    }

}

