package com.example.demo.pojo.dto;

import lombok.Data;

@Data
public class CalendarResponseDto {

    private Integer remainder;
    private Integer existing;
    private String ymd;
    private Integer total;

    public Integer getRemainder() {
        return remainder;
    }

    public void setRemainder(Integer remainder) {
        this.remainder = remainder;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public Integer getExisting() {
        return existing;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setExisting(Integer existing) {
        this.existing = existing;
    }

    public CalendarResponseDto() {
    }

    public CalendarResponseDto(Integer remainder, String ymd) {
        this.remainder = remainder;
        this.ymd = ymd;
    }
    public CalendarResponseDto(String ymd){
        this.ymd=ymd;
    }
}
