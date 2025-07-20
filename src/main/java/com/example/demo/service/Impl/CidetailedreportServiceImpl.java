package com.example.demo.service.Impl;

import com.example.demo.exception.TijianException;
import com.example.demo.mapper.*;
import com.example.demo.pojo.Cidetailedreport;
import com.example.demo.pojo.Cireport;
import com.example.demo.pojo.Orders;
import com.example.demo.pojo.Overallresult;
import com.example.demo.pojo.dto.CireportDTO;
import com.example.demo.pojo.vo.CireportVO;
import com.example.demo.pojo.vo.TjianReportVO;
import com.example.demo.result.Result;
import com.example.demo.service.CidetailedreportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CidetailedreportServiceImpl implements CidetailedreportService {
    @Autowired
    CidetailedreportMapper cidetailedreportMapper;
    @Autowired
    CireportMapper cireportMapper;
    @Autowired
    CheckitemMapper checkitemMapper;
    @Autowired
    CheckitemdetailedMapper checkitemdetailedMapper;
    @Autowired
    OverallresultMapper overallresultMapper;

    @Transactional
    public void createReportTemplate(Orders orders) {
        int count = cireportMapper.selectOrderTemplate(orders.getOrderid());
        if (count > 0){
            throw new TijianException(407,"订单号已存在");
        }
        List<Cireport> cireports = checkitemMapper.selectCheckItem(orders.getSmid());
        for (Cireport cireport:cireports){
            cireport.setOrderid(orders.getOrderid());
        }
        cireportMapper.insertBatch(cireports);

        List<Cidetailedreport> cidetailedreports = checkitemdetailedMapper.selectCheckItemDetailed(orders.getSmid());
        for (Cidetailedreport cidetailedreport:cidetailedreports){
            cidetailedreport.setOrderid(orders.getOrderid());
        }
        cidetailedreportMapper.insertBatch(cidetailedreports);
    }

    public Result<List<CireportVO>> getReportTemplate(String orderId) {
        List<Cireport> cireports = cireportMapper.selectCheckItem(orderId);
        List<CireportVO> list = new ArrayList<>();
        for (Cireport cireport01:cireports){
            CireportVO cireport = new CireportVO();
            cireport.setCrid(cireport01.getCiid());
            cireport.setCiname(cireport01.getCiname());
            List<Cidetailedreport> reportTemplate = cidetailedreportMapper.getReportTemplate(orderId, cireport01.getCiid());
            cireport.setCidetailedreports(reportTemplate);
            list.add(cireport);
        }
        return Result.success(list);
    }

    public void updatavValue(CireportDTO cireportDTO) {
        cidetailedreportMapper.updateByPrimaryKeySelectiveBatch(cireportDTO);
    }

    @Override
    public TjianReportVO getCidetaildreport(String orderid) {
        List<Cidetailedreport> cidetaildreports = cidetailedreportMapper.getCidetaildreports(orderid);
        List<Overallresult> allOverallresultList = overallresultMapper.getAllOverallresultList(orderid);
        TjianReportVO tjianReportVO = new TjianReportVO();
        tjianReportVO.setCidetailedreports(cidetaildreports);
        tjianReportVO.setOverallresults(allOverallresultList);
        return tjianReportVO;
    }
}
