package com.example.demo.controller.user;

import com.example.demo.constant.JwtClaimsConstant;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.pojo.Users;
import com.example.demo.pojo.vo.UserLoginVO;
import com.example.demo.properties.JwtProperties;
import com.example.demo.result.Result;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.SMSUtil;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
@Api(tags = "短信验证登录")
public class SmsController {
    @Autowired
    SMSUtil smsUtil;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    JwtProperties jwtProperties;

    @PostMapping("/sendSMS")
    @ApiOperation("发送短信")
    public Result sendSMS(@RequestBody Map<String,String> map){
        String phone = map.get("phone");
        Users user = usersMapper.selectByPrimaryKey(phone);
        if(user == null){
            return Result.error("手机号未注册");
        }
        String sms = smsUtil.generateSMS(phone);
        if(!Strings.isNullOrEmpty(sms)){
            return Result.success(sms);
        }
        return Result.error("短信发送失败");
    }

    @GetMapping("/checkSMS")
    @ApiOperation("验证短信验证码")
    public Result checkSMS( String phone, String sms){
        boolean result = smsUtil.verifySMS(phone, sms);
        log.info("电话号:{},验证码:{}",phone,sms);
        if(result){
            Users user = usersMapper.selectByPrimaryKey(phone);
            Map<String,Object> claims = new HashMap();
            claims.put(JwtClaimsConstant.USER_ID,user.getUserid());
            String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
            UserLoginVO userLoginVO = UserLoginVO.builder()
                    .users(user)
                    .token(token)
                    .build();
            return Result.success(userLoginVO);
        }
        return Result.error("验证码错误");
    }
}
