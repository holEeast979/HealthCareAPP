package com.example.demo.pojo.vo;

import com.example.demo.pojo.Hospital;
import com.example.demo.pojo.Setmeal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPageVO {
    private String orderid;

    private String userid;

    private String realname;

    private Integer sex;

    private String sname;

    private String hname;

    private String orderdate;

    private Integer state;

    private Integer smid;
}
