package com.example.demo.controller.user;

import com.example.demo.result.Result;
import com.example.demo.service.DeepSeekService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Api(tags = "接入Deepseek相关接口")
@Slf4j
@RestController
@RequestMapping("/deepseek")
public class DeepSeekController {

    @Autowired
    DeepSeekService deepSeekService;

    @PostMapping("/getDeepSeekResponse")
    @ApiOperation("获取Deepseek回复")
    public Result<String> getDeepSeekResponse(@RequestBody String message) {
        log.info("发送信息给ai时间{}", LocalDateTime.now());
        return Result.success(deepSeekService.call(message));
    }
}
