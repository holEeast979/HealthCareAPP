package com.example.demo.exception;

/**
 * 异常类
 */
public class TijianException extends RuntimeException{  //不定义异常类也可以，new  RuntimeException

    //不需要手动catch

    private Integer code;
    private String message;

    public TijianException(){}
    public TijianException(Integer code,String message){
        this.code=code;
        this.message=message;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}