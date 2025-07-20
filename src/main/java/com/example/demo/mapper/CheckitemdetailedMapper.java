package com.example.demo.mapper;

import com.example.demo.pojo.Checkitemdetailed;
import com.example.demo.pojo.Cidetailedreport;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckitemdetailedMapper {
    int deleteByPrimaryKey(Integer cdid);

    int insert(Checkitemdetailed record);

    int insertSelective(Checkitemdetailed record);

    Checkitemdetailed selectByPrimaryKey(Integer cdid);

    int updateByPrimaryKeySelective(Checkitemdetailed record);

    int updateByPrimaryKey(Checkitemdetailed record);

    List<Cidetailedreport> selectCheckItemDetailed(Integer smId);
}