package com.example.demo.service.Impl;

import com.example.demo.mapper.DoctorMapper;
import com.example.demo.pojo.Doctor;
import com.example.demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorMapper doctorMapper;

    public Doctor login(Doctor doctor) {
        return doctorMapper.SelectByDoctorDoccode(doctor);
    }
}
