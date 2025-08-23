package com.example.demo.pojo;

public class Cireport {
    private Integer cirid;

    private Integer ciid;

    private String ciname;

    private String orderid;

    public Integer getCirid() {
        return cirid;
    }

    public void setCirid(Integer cirid) {
        this.cirid = cirid;
    }

    public Integer getCiid() {
        return ciid;
    }

    public void setCiid(Integer ciid) {
        this.ciid = ciid;
    }

    public String getCiname() {
        return ciname;
    }

    public void setCiname(String ciname) {
        this.ciname = ciname == null ? null : ciname.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}