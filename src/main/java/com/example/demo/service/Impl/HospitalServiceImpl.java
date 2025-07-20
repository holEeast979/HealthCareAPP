package com.example.demo.service.Impl;

import com.example.demo.mapper.HospitalMapper;
import com.example.demo.pojo.Hospital;
import com.example.demo.result.Result;
import com.example.demo.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    HospitalMapper hospitalMapper;

    public List<Hospital> showhospital() {

        return hospitalMapper.showhospital();
    }

    public Hospital gethospitalById(Integer id) {
        return hospitalMapper.selectByPrimaryKey(id);
    }
}
