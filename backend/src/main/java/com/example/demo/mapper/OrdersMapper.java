package com.example.demo.mapper;

import com.example.demo.pojo.Orders;
import com.example.demo.pojo.Users;
import com.example.demo.pojo.dto.CalendarResponseDto;
import com.example.demo.pojo.dto.OrderSetmealDto;
import com.example.demo.pojo.dto.OrdersMapperDto;
import com.example.demo.pojo.dto.OrdersPageDto;
import com.example.demo.pojo.vo.OrderPageVO;
import com.example.demo.pojo.vo.TjianVO;
import com.example.demo.result.Result;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrdersMapper {
    int deleteByPrimaryKey(Integer orderid);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(String orderid);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    List<CalendarResponseDto> listOrdersAppointmentNumber(List<OrdersMapperDto> parameList);

    @Select("select o.orderId ,s.name ,o.orderDate ,o.state ,s.price " +
            "from orders o,setmeal s where s.smId = o.smId and o.userId = #{userid} and o.state=2")
    List<OrderSetmealDto> findByUserId(OrderSetmealDto order);

    @Select("select orderId, orderDate, userId, hpId, smId, state from orders where orderDate=date(#{orderdate}) and userId=#{userid}")
    Orders selectuserorder(Orders orders);

    @Update("update orders set state=#{state} where orderId=#{orderid}")
    int updatastate(Orders orders);

    @Select("select state from orders where orderId = #{orderid}")
    Orders getstate(Orders orders);
    //分页查询
    Page<OrderPageVO> pageQuery(OrdersPageDto ordersPageDto);

    @Select("select o.orderId,o.orderDate,h.name from orders o,hospital h where o.userId =#{userid} and h.hpId = o.hpId and o.state=3")
    List<TjianVO> getTjianlist(String userid);
}
