package com.example.demo.mapper;

import com.example.demo.pojo.Overallresult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OverallresultMapper {
    int deleteByPrimaryKey(Integer orid);

    int insert(Overallresult record);

    int insertSelective(Overallresult record);

    Overallresult selectByPrimaryKey(Integer orid);

    int updateByPrimaryKeySelective(Overallresult record);

    int updateByPrimaryKey(Overallresult record);

    @Select("select orId, title, content, orderId from overallresult where orderId=#{orderid}")
    List<Overallresult> getAllOverallresultList(String orderid);

    @Select("select orId, title, content, orderId from overallresult where orderId=#{orderid}")
    List<Overallresult> updateByOrderId(String orderid);
}