package com.example.demo.pojo;

public class Checkitem {
    private Integer ciid;

    private String ciname;

    private String cicontent;

    private String meaning;

    private String remarks;

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

    public String getCicontent() {
        return cicontent;
    }

    public void setCicontent(String cicontent) {
        this.cicontent = cicontent == null ? null : cicontent.trim();
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning == null ? null : meaning.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}