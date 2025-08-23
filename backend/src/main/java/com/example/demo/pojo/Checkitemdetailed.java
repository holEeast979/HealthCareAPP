package com.example.demo.pojo;

public class Checkitemdetailed {
    private Integer cdid;

    private String name;

    private String unit;

    private Double minrange;

    private Double maxrange;

    private String normalvalue;

    private String normalvaluestring;

    private Integer type;

    private Integer ciid;

    private String remarks;

    public Integer getCdid() {
        return cdid;
    }

    public void setCdid(Integer cdid) {
        this.cdid = cdid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Double getMinrange() {
        return minrange;
    }

    public void setMinrange(Double minrange) {
        this.minrange = minrange;
    }

    public Double getMaxrange() {
        return maxrange;
    }

    public void setMaxrange(Double maxrange) {
        this.maxrange = maxrange;
    }

    public String getNormalvalue() {
        return normalvalue;
    }

    public void setNormalvalue(String normalvalue) {
        this.normalvalue = normalvalue == null ? null : normalvalue.trim();
    }

    public String getNormalvaluestring() {
        return normalvaluestring;
    }

    public void setNormalvaluestring(String normalvaluestring) {
        this.normalvaluestring = normalvaluestring == null ? null : normalvaluestring.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCiid() {
        return ciid;
    }

    public void setCiid(Integer ciid) {
        this.ciid = ciid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}