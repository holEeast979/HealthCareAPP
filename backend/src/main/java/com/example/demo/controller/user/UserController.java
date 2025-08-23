package com.example.demo.controller.user;

import com.example.demo.constant.JwtClaimsConstant;
import com.example.demo.pojo.Users;
import com.example.demo.pojo.vo.UserLoginVO;
import com.example.demo.properties.JwtProperties;
import com.example.demo.result.Result;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户接口")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtProperties jwtProperties;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody Users user){
        String userId = user.getUserid();
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        log.info("用户名：{}，密码：{}",userId,password);
        user.setPassword(password);
        Users us = userService.login(user);
        if (us == null){
            return Result.error("该手机号未注册");
        }else if (!us.getPassword().equals(password)){
            return Result.error("密码错误");
        }
        Map<String,Object> claims = new HashMap();
        claims.put(JwtClaimsConstant.USER_ID,us.getUserid());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .users(us)
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public Result<Users> register(@RequestBody Users user){
        log.info("用户信息:{}",user.toString());
        return userService.register(user);
    }

    @ApiOperation("判断用户是否注册")
    @PostMapping("/isregister")
    public @ResponseBody Result<Users> isregister(@RequestBody Users user){
        return userService.isregister(user);
    }


    @ApiOperation("修改用户信息")
    @PutMapping("/update")
    public Result<Users> update(@RequestBody Users user){
        log.info("用户信息:{}",user.toString());
        return userService.update(user);
    }
}
