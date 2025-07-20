package com.example.demo.pojo.dto;

import lombok.Data;

@Data
public class OrdersMapperDto {
    private String date;
    private Integer hpid;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getHpid() {
        return hpid;
    }

    public void setHpid(Integer hpid) {
        this.hpid = hpid;
    }
}
