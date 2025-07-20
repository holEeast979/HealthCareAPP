package com.example.demo.controller.doctor;

import com.example.demo.constant.JwtClaimsConstant;
import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.vo.DoctorLoginVO;
import com.example.demo.pojo.vo.UserLoginVO;
import com.example.demo.properties.JwtProperties;
import com.example.demo.result.Result;
import com.example.demo.service.DoctorService;
import com.example.demo.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "医生接口")
@Slf4j
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;
    @Autowired
    JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("医生登录")
    public Result<DoctorLoginVO> login(@RequestBody Doctor doctor){
        String mdpassword = DigestUtils.md5DigestAsHex(doctor.getPassword().getBytes());
        doctor.setPassword(mdpassword);
        Doctor doctor1 = doctorService.login(doctor);
        log.info("医生信息:{}",doctor1.toString());
        if (doctor1 == null){
            return Result.error("系统没有该医生");
        }else if (!doctor1.getPassword().equals(mdpassword)){
            return Result.error("密码错误");
        }
        Map<String,Object> claims = new HashMap();
        claims.put(JwtClaimsConstant.USER_ID,doctor1.getDocid());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        DoctorLoginVO doctorLoginVO = DoctorLoginVO.builder()
                .doctor(doctor1)
                .token(token)
                .build();
        return Result.success(doctorLoginVO);
    }

}
