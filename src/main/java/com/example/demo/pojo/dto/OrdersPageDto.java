package com.example.demo.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersPageDto {
    //页码
    private int page;

    //每页记录数
    private int pageSize;

    private String userid;

    private String realname;

    private Integer sex;

    private String name;//套餐名称
//
//    @JsonFormat(timezone = "yyyy-MM-dd")
    private String orderdate;

    private Integer state;
}
