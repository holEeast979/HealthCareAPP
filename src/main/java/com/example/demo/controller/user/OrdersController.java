package com.example.demo.controller.user;

import com.example.demo.pojo.Orders;
import com.example.demo.pojo.Users;
import com.example.demo.pojo.dto.OrderSetmealDto;
import com.example.demo.pojo.vo.TjianVO;
import com.example.demo.result.Result;
import com.example.demo.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "预约订单接口")
@Slf4j
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    @PostMapping("/getorders")
    @ApiOperation("获取预约订单")
    public @ResponseBody Result<List<OrderSetmealDto>> finderByUserId(@RequestBody OrderSetmealDto order){
        List<OrderSetmealDto> orders = ordersService.finderByUserId(order);
        log.info("获取预约订单:{}",orders.toString());
        return Result.success(orders);
    }

    @PostMapping("/addorders")
    @ApiOperation("添加预约订单")
    public Result<Orders> addOrders(@RequestBody Orders orders){
        return ordersService.saveOrder(orders);
    }

    @PostMapping("/updataOrderstate")
    @ApiOperation("取消预约订单")
    public @ResponseBody Result updataOrderstate(@RequestBody Orders orders){
        return ordersService.updateOrderstate(orders);
    }

    @PostMapping("/getstate")
    @ApiOperation("获取订单状态")
    public Result<Orders> getstate(@RequestBody Orders orders){
        Orders order = ordersService.getstate(orders);
        log.info("订单状态：{}",order.getState());
        return Result.success(order);
    }


    @GetMapping("/getTjianlist")
    @ApiOperation("获取体检报告列表")
    public Result<List<TjianVO>> getTjianlist(String userid) {
        List<TjianVO> tjianVO = ordersService.getTjianlist(userid);
        return Result.success(tjianVO);
    }
}

