package com.example.demo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CireportDTO implements Serializable {

    private Integer cidrid;

    private Integer iserror;

    private String value;

}
