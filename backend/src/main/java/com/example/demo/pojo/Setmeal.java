package com.example.demo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Setmeal {
    private Integer smid;

    private String name;

    private Integer type;

    private Integer price;

    private List<Checkitem> checkitemList;

    public List<Checkitem> getCheckitemList() {
        return checkitemList;
    }

    public void setCheckitemList(List<Checkitem> checkitemList) {
        this.checkitemList = checkitemList;
    }

    public Integer getSmid() {
        return smid;
    }

    public void setSmid(Integer smid) {
        this.smid = smid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}