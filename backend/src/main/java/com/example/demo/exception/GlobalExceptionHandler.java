package com.example.demo.exception;

import com.example.demo.result.ApiResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 */
@ControllerAdvice  //这个注解必须要有
public class GlobalExceptionHandler {  //当前类中的异常处理接口方法，只要程序出异常，方法就会被触发

    /*
    处理业务异常的方法
     */

    @ExceptionHandler(TijianException.class)
    @ResponseBody  //返回json格式数据给前端
    public ApiResponse handleTijianAppException(TijianException e){  //该方法返回ApiResponse意味着方法会被前端调用到，
        e.printStackTrace();  //控制台打印输出异常堆栈信息
        System.out.println("异常信息："+e.getStackTrace());

        return new ApiResponse(e.getCode(),e.getMessage());

    }

    /*
    处理业务异常之外的其它异常的方法
     */

    @ExceptionHandler(Exception.class)
    @ResponseBody  //返回json格式数据给前端
    public ApiResponse handleAllException(Exception e){
        e.printStackTrace();
        System.out.println("异常信息："+e.getStackTrace()+",message:"+e.getMessage());
        return new ApiResponse(TijianExceptionEnum.SYSTEM_ERROR.getCode(),TijianExceptionEnum.SYSTEM_ERROR.getMessage());  //兜底的异常处理方法

    }
}