package com.example.demo.service.Impl;

import com.example.demo.exception.TijianException;
import com.example.demo.exception.TijianExceptionEnum;
import com.example.demo.mapper.CidetailedreportMapper;
import com.example.demo.mapper.OrdersMapper;
import com.example.demo.mapper.OverallresultMapper;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.pojo.Cidetailedreport;
import com.example.demo.pojo.Orders;
import com.example.demo.pojo.Overallresult;
import com.example.demo.pojo.Users;
import com.example.demo.pojo.dto.OrderSetmealDto;
import com.example.demo.pojo.dto.OrdersPageDto;
import com.example.demo.pojo.vo.OrderPageVO;
import com.example.demo.pojo.vo.TjianVO;
import com.example.demo.result.PageResult;
import com.example.demo.result.Result;
import com.example.demo.service.OrdersService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    CidetailedreportMapper cidetailedreportMapper;
    @Autowired
    OverallresultMapper overallresultMapper;

    public List<OrderSetmealDto> finderByUserId(OrderSetmealDto order) {
        List<OrderSetmealDto> orders = ordersMapper.findByUserId(order);
        for (OrderSetmealDto od : orders){
            Long currentTime=new Date().getTime();   //当前时间
            Long orderTime=od.getOrderdate().getTime(); //订单时间
            Float differentTime=(orderTime-currentTime)/(1000*60*60*24)*1F;
            if(differentTime<=3){
                od.setIsCancel(0);
            }else{
                od.setIsCancel(1);
            }
        }
        return orders;
    }

    public Result<Orders> saveOrder(Orders orders) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(orders.getOrderdate());
        log.info("时间：{}、用户电话：{}",formattedDate,orders.getUserid());
        Orders userorder = ordersMapper.selectuserorder(orders);
        String time = new SimpleDateFormat("HHmmss").format(new Date());
        String user = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String OrderNum = time + user.substring(0,8) ;
        log.info("订单号{}",OrderNum);
        orders.setOrderid(OrderNum);
        if (userorder != null) {
            return Result.error("当日已有预约");
        }
        ordersMapper.insert(orders);
        log.info("订单详情{}",orders);
        return Result.success(orders);
    }

    public Orders getOrder(String orderid) {
        return ordersMapper.selectByPrimaryKey(orderid);
    }

    public Orders updateOrder(Orders orderModels) {
        ordersMapper.updateByPrimaryKeySelective(orderModels);
        return orderModels;
    }

    public Orders getstate(Orders orders) {
        return ordersMapper.getstate(orders);
    }

    public Result updateOrderstate(Orders orders) {
        Orders orders_result=ordersMapper.selectByPrimaryKey(orders.getOrderid());
            //3.取消订单并返回成功
            orders_result.setState(4);
            ordersMapper.updateByPrimaryKeySelective(orders_result);
            return Result.success();
    }

    public PageResult pageQuery(OrdersPageDto ordersPageDto) {
        PageHelper.startPage(ordersPageDto.getPage(),ordersPageDto.getPageSize());
        //下一条sql进行分页，自动加入limit关键字分页
        Page<OrderPageVO> page = ordersMapper.pageQuery(ordersPageDto);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public Result updateState(Orders orderId) {
        List<Cidetailedreport> cidetailedreports = cidetailedreportMapper.selectByOrderId(orderId.getOrderid());
        for (Cidetailedreport cidetailedreport : cidetailedreports){
            if (cidetailedreport.getValue() == null || cidetailedreport.getValue().equals("")){
                return Result.error("有检查项未完成！");
            }
        }
        List<Overallresult> overallresults = overallresultMapper.updateByOrderId(orderId.getOrderid());
        if (overallresults.size() == 0){
            return Result.error("请补全总检结论！");
        }
        ordersMapper.updatastate(orderId);
        return Result.success();
    }

    public List<TjianVO> getTjianlist(String userid) {
        return ordersMapper.getTjianlist(userid);
    }
}
