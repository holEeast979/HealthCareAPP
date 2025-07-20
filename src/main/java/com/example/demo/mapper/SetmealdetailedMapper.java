package com.example.demo.mapper;

import com.example.demo.pojo.Setmealdetailed;

public interface SetmealdetailedMapper {
    int deleteByPrimaryKey(Integer sdid);

    int insert(Setmealdetailed record);

    int insertSelective(Setmealdetailed record);

    Setmealdetailed selectByPrimaryKey(Integer sdid);

    int updateByPrimaryKeySelective(Setmealdetailed record);

    int updateByPrimaryKey(Setmealdetailed record);
}