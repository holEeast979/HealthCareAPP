package com.example.demo.utils;

import cn.hutool.captcha.generator.RandomGenerator;
import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.example.demo.model.SMSModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class SMSUtil {
    @Autowired SMSModel smsModel;
    @Autowired RedisTemplate redisTemplate;

    public String generateSMS(String phone){
        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = smsModel.getAccountId();
        String accountToken = smsModel.getAuthToken();
        //请使用管理控制台中已创建应用的APPID
        String appId = smsModel.getAppId();
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        String to = phone;
        String templateId= "1";
        RandomGenerator randomGenerator = new RandomGenerator("0123456789",4);
        String randomNum = randomGenerator.generate();
        Long expre = 2L;
        String[] datas = {randomNum,expre.toString()};
        HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
//        HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas,subAppend,reqId);
        if("000000".equals(result.get("statusCode"))){
            redisTemplate.opsForValue().set(phone,randomNum);

            redisTemplate.expire(phone,10000L, TimeUnit.SECONDS);
            return randomNum;
        }else{
            return null;
        }
    }

    public String getsmsm(String phone){
        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = smsModel.getAccountId();
        String accountToken = smsModel.getAuthToken();
        //请使用管理控制台中已创建应用的APPID
        String appId = smsModel.getAppId();
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        String to = phone;
        String templateId= "1";
        String msg = "成功";
        String[] datas = {msg};
        HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
//        HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas,subAppend,reqId);
        if("000000".equals(result.get("statusCode"))){
            return msg;
        }else{
            return null;
        }
    }

    public boolean verifySMS(String phone, String sms){
        Object obj = redisTemplate.opsForValue().get(phone);
        if(Objects.isNull(obj)){
            throw new RuntimeException("验证码已过期");
        }
        String sms_redis = (String) obj;
        return sms_redis.equals(sms);
    }

}
