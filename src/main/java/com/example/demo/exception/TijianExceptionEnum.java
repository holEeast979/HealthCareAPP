package com.example.demo.exception;

/*
体检APP的使用手册：  所有异常信息都在里面定义
 */
public enum TijianExceptionEnum {
    MOBILE_NOT_REISTER(201,"手机号尚未注册"),
    PASSWORD_IS_WRONG(202,"密码是错误的"),
    SYSTEM_ERROR(203,"系统错误"),
    EXCEEDING_CANCEL_DEADLINE(204,"超过取消订单的截止时间");

    /**
     * 枚举类中定义了使用的异常编码和提示信息
     */

    private Integer code;
    private String message;

    TijianExceptionEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}