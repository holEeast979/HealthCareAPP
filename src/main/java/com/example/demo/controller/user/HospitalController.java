package com.example.demo.controller.user;

import com.example.demo.pojo.Hospital;
import com.example.demo.result.Result;
import com.example.demo.service.HospitalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "医院相关接口")
@Slf4j
@RestController
@RequestMapping("/hospital")
public class HospitalController {
    @Autowired
    HospitalService hospitalService;

    @ApiOperation("医院选择")
    @GetMapping("/showhospital")
    public Result<List<Hospital>> showhospital(){
        List<Hospital> hospital = hospitalService.showhospital();
        return Result.success(hospital);
    }

    @ApiOperation("获取医院详情")
    @GetMapping("/gethospitalById")
    public Result<Hospital> gethospitalById( Integer id){
        Hospital hospital = hospitalService.gethospitalById(id);
        log.info("医院编号：{}",id);
        return Result.success(hospital);
    }

}
