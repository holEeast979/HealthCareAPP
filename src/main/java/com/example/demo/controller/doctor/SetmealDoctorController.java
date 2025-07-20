package com.example.demo.controller.doctor;


import com.example.demo.pojo.Setmeal;
import com.example.demo.result.Result;
import com.example.demo.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "套餐相关接口")
@Slf4j
@RestController
@RequestMapping("doctor/setmeal")
public class SetmealDoctorController {

    @Autowired
    SetmealService setmealService;

    @PostMapping("/list")
    @ApiOperation("获取套餐列表")
    public Result<List<Setmeal>> list(){
        List<Setmeal> setmeallist = setmealService.setmeallist();
        return Result.success(setmeallist);
    }
}
