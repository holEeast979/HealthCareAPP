package com.example.demo.mapper;

import com.example.demo.pojo.Setmeal;
import com.example.demo.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {
    int deleteByPrimaryKey(Integer smid);

    int insert(Setmeal record);

    int insertSelective(Setmeal record);

    Setmeal selectByPrimaryKey(Integer smid);

    int updateByPrimaryKeySelective(Setmeal record);

    int updateByPrimaryKey(Setmeal record);

    List<Setmeal> getBysexSetmel(Users users);

    @Select("select name from setmeal")
    List<Setmeal> seteamllist();

}