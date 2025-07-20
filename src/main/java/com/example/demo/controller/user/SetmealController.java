package com.example.demo.controller.user;


import com.example.demo.pojo.Hospital;
import com.example.demo.pojo.Setmeal;
import com.example.demo.pojo.Users;
import com.example.demo.result.Result;
import com.example.demo.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "套餐接口")
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @ApiOperation("套餐详情")
    @PostMapping("/getSetmeal")
    public @ResponseBody Result<List<Setmeal>> getSetmeal(@RequestBody Users users){
        List<Setmeal> setmealList = setmealService.getSetmeal(users);
        log.info("套餐详情:{}",setmealList.toString());
        return Result.success(setmealList);
    }

    @GetMapping("/findbysmId")
    @ApiOperation("获取选中套餐")
    public Result<Setmeal> getusersetmeal(Integer smId){
        Setmeal setmeal = setmealService.findbysmId(smId);
        return Result.success(setmeal);
    }
}
