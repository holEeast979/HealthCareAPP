package com.example.demo.service;

import com.example.demo.pojo.Cidetailedreport;
import com.example.demo.pojo.Orders;
import com.example.demo.pojo.dto.CireportDTO;
import com.example.demo.pojo.vo.CireportVO;
import com.example.demo.pojo.vo.TjianReportVO;
import com.example.demo.result.Result;

import java.util.List;

public interface CidetailedreportService {
    void createReportTemplate(Orders orders);

    Result<List<CireportVO>> getReportTemplate(String orderId);

    void updatavValue(CireportDTO cireportDTO);

    TjianReportVO getCidetaildreport(String orderid);
}
