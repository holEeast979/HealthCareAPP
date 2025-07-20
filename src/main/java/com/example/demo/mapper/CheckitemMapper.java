package com.example.demo.mapper;

import com.example.demo.pojo.Checkitem;
import com.example.demo.pojo.Cireport;
import com.example.demo.pojo.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CheckitemMapper {
    int deleteByPrimaryKey(Integer ciid);

    int insert(Checkitem record);

    int insertSelective(Checkitem record);

    Checkitem selectByPrimaryKey(Integer ciid);

    int updateByPrimaryKeySelective(Checkitem record);

    int updateByPrimaryKey(Checkitem record);

    @Select("select c.ciId,c.ciName,c.ciContent,c.meaning,c.remarks\n" +
            "    from checkitem c,setmeal s,setmealdetailed sd\n" +
            "    where s.smId = sd.smId and sd.ciId = c.ciId and s.smId=#{smid}")
    List<Checkitem> getCheckitem(Setmeal setmeal);

    List<Cireport> selectCheckItem(Integer smId);
}