package com.example.demo.pojo.vo;

import com.example.demo.pojo.Cidetailedreport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CireportVO {
    private String ciname;
    private Integer crid;
    private List<Cidetailedreport> cidetailedreports;
}
