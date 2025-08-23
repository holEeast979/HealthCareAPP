package com.example.demo.mapper;

import com.example.demo.pojo.Doctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DoctorMapper {
    int deleteByPrimaryKey(Integer docid);

    int insert(Doctor record);

    int insertSelective(Doctor record);

    Doctor selectByPrimaryKey(Integer docid);

    int updateByPrimaryKeySelective(Doctor record);

    int updateByPrimaryKey(Doctor record);

    @Select("select docId, docCode, realName, password, sex, deptno from doctor where docCode = #{doccode}")
    Doctor SelectByDoctorDoccode(Doctor doctor);
}