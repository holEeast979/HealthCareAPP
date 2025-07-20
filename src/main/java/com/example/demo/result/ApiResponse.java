package com.example.demo.result;

/**
 * 前端和后端一起统一返回响应的数据类型
 * @param <T>
 */
public class ApiResponse<T> {

    private Integer status;
    private String desc;   //message
    private T data;  //User  Order  Report

    //构造方法
    public ApiResponse(){}

    public ApiResponse(Integer status,String desc,T data){
        this.status=status;
        this.desc=desc;
        this.data=data;
    }

    public ApiResponse(Integer status,String desc){
        this.status=status;
        this.desc=desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}