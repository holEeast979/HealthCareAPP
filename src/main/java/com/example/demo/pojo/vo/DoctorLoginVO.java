package com.example.demo.pojo.vo;

import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorLoginVO implements Serializable {

    private Doctor doctor;
    private String token;

}
