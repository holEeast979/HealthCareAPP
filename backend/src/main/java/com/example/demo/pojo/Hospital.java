package com.example.demo.pojo;

public class Hospital {
    private Integer hpid;

    private String name;

    private String telephone;

    private String address;

    private String businesshours;

    private String deadline;

    private String rule;

    private Integer state;

    private String picture;

    public Integer getHpid() {
        return hpid;
    }

    public void setHpid(Integer hpid) {
        this.hpid = hpid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getBusinesshours() {
        return businesshours;
    }

    public void setBusinesshours(String businesshours) {
        this.businesshours = businesshours == null ? null : businesshours.trim();
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline == null ? null : deadline.trim();
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }
}