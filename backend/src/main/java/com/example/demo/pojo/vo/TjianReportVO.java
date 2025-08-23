package com.example.demo.pojo.vo;

import com.example.demo.pojo.Cidetailedreport;
import com.example.demo.pojo.Overallresult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TjianReportVO {
    private List<Overallresult> overallresults; //体检结论数据
    private List<Cidetailedreport> cidetailedreports;  //体检大项及各个小项数据
}
