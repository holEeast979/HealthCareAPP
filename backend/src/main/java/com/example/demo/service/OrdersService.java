package com.example.demo.service;

import com.example.demo.pojo.Orders;
import com.example.demo.pojo.Users;
import com.example.demo.pojo.dto.OrderSetmealDto;
import com.example.demo.pojo.dto.OrdersPageDto;
import com.example.demo.pojo.vo.TjianVO;
import com.example.demo.result.PageResult;
import com.example.demo.result.Result;

import java.util.List;

public interface OrdersService {
    List<OrderSetmealDto> finderByUserId(OrderSetmealDto order);

    Result<Orders> saveOrder(Orders orders);

    Orders getOrder(String orderid);

    Orders updateOrder(Orders orderModels);

    Orders getstate(Orders orders);

    Result updateOrderstate(Orders orders);

    PageResult pageQuery(OrdersPageDto ordersPageDto);

    Result updateState(Orders orderId);

    List<TjianVO> getTjianlist(String userid);
}
