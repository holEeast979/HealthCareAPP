package com.example.demo.mapper;

import com.example.demo.pojo.Cidetailedreport;
import com.example.demo.pojo.Cireport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CireportMapper {
    int deleteByPrimaryKey(Integer cirid);

    int insert(Cireport record);

    int insertSelective(Cireport record);

    Cireport selectByPrimaryKey(Integer cirid);

    int updateByPrimaryKeySelective(Cireport record);

    int updateByPrimaryKey(Cireport record);

    int insertBatch(List<Cireport> list);

    int selectOrderTemplate(String orderid);

    @Select("select ciId, ciName from cireport where orderid=#{orderid}")
    List<Cireport> selectCheckItem(String orderid);
}