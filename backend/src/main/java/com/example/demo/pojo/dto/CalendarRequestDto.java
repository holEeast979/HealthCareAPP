package com.example.demo.pojo.dto;

import lombok.Data;

@Data
public class CalendarRequestDto {

    private Integer year;
    private Integer month;
    private Integer hpid;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getHpid() {
        return hpid;
    }

    public void setHpid(Integer hpid) {
        this.hpid = hpid;
    }

    public CalendarRequestDto(Integer year, Integer month, Integer hpid) {
        this.year = year;
        this.month = month;
        this.hpid = hpid;
    }

    public CalendarRequestDto() {
    }
}
