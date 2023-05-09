package com.smart.agriculture.controller;

import com.smart.agriculture.common.result.CommonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class CommonController {
    @PostMapping("/test")
    public CommonResult s(){
        return CommonResult.failed();
    }
}
