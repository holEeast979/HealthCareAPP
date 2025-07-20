package com.example.demo.controller.doctor;

import com.example.demo.pojo.Orders;
import com.example.demo.pojo.dto.OrdersPageDto;
import com.example.demo.result.PageResult;
import com.example.demo.result.Result;
import com.example.demo.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "预约订单相关接口")
@Slf4j
@RestController
@RequestMapping("doctor/orders")
public class OrderDoctorController {

    @Autowired
    OrdersService ordersService;


    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(OrdersPageDto ordersPageDto){
        log.info("分页查询：{}", ordersPageDto);
        PageResult pageResult = ordersService.pageQuery(ordersPageDto);
        return Result.success(pageResult);
    }

    @PostMapping("/updatestate")
    @ApiOperation("归档订单状态")
    public@ResponseBody Result updateState(@RequestBody Orders orderId){
        return ordersService.updateState(orderId);
    }
}
