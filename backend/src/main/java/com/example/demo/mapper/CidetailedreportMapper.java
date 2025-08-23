package com.example.demo.mapper;

import com.example.demo.pojo.Cidetailedreport;
import com.example.demo.pojo.dto.CireportDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CidetailedreportMapper {
    int deleteByPrimaryKey(Integer cidrid);

    int insert(Cidetailedreport record);

    int insertSelective(Cidetailedreport record);

    Cidetailedreport selectByPrimaryKey(Integer cidrid);

    int updateByPrimaryKeySelective(Cidetailedreport record);

    int updateByPrimaryKey(Cidetailedreport record);

    int insertBatch(List<Cidetailedreport> list);

    @Select("select cidrId, name, unit, minrange, maxrange, normalValue, normalValueString, " +
            "type, value, isError, ciId, orderId from cidetailedreport where " +
            "orderId=#{orderId} and ciId = #{ciId}")
    List<Cidetailedreport> getReportTemplate(String orderId,Integer ciId);

    @Update("update cidetailedreport\n" +
            "   set value = #{value}\n" +
            "   ,isError = #{iserror}\n" +
            "    where cidrId = #{cidrid}")
    void updateByPrimaryKeySelectiveBatch(CireportDTO cireportDTO);

    @Select("select value from cidetailedreport where orderId =#{orderid}")
    List<Cidetailedreport> selectByOrderId(String orderid);

    @Select("select cidrId, name, unit, minrange, maxrange,\n" +
            "       normalValue, normalValueString, type,\n" +
            "       value, isError, ciId, orderId from\n" +
            "cidetailedreport where orderId =#{orderid}")
    List<Cidetailedreport> getCidetaildreports(String orderid);
}