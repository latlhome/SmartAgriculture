package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.UserCollection.IsCollectionDto;
import com.smart.agriculture.Dto.UserFollow.IsFollowDto;
import com.smart.agriculture.Vo.UserFollow.SelectUserFollowListVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IFreedomArticleService;
import com.smart.agriculture.service.IUserCollectionService;
import com.smart.agriculture.service.IUserFollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 点赞收藏关注
 * @author ylx
 * @since 2023-05-19
 */
@RestController
@RequestMapping("/userFollow")
@Api(tags = "点赞 关注 收藏")
public class UserFollowController {
    @Resource
    private IUserFollowService followService;
    @Resource
    private IUserCollectionService collectionService;
    @Resource
    private IFreedomArticleService freedomArticleService;
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

    @PutMapping("/collection")
    @ApiOperation("收藏 or 取消")
    public CommonResult<String> collection(IsCollectionDto dto){
        return collectionService.collection(dto);
    }
    @GetMapping("/likeFreedomArticle/{id}")
    @ApiOperation("给帖子点赞 --传入帖子ID")
    public CommonResult<String> likeFreedomArticle(@PathVariable String id){
        return freedomArticleService.likeFreedomArticle(id);
    }

}

