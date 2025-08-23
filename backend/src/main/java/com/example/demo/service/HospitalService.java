package com.example.demo.service;

import com.example.demo.pojo.Hospital;
import com.example.demo.result.Result;

import java.util.List;

public interface HospitalService {
    List<Hospital> showhospital();

    Hospital gethospitalById(Integer id);
}
