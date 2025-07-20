package com.example.demo.controller.user;

import com.example.demo.pojo.Cidetailedreport;
import com.example.demo.pojo.vo.TjianReportVO;
import com.example.demo.result.Result;
import com.example.demo.service.CidetailedreportService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "体检报告明细相关接口")
@Slf4j
@RestController
@RequestMapping("/user/cidetaildreport")
public class CidetaildreportUserController {

    @Autowired
    CidetailedreportService cidetailedreportService;

    @GetMapping("/getCidetaildreport")
    public Result<TjianReportVO> getCidetaildreport(String orderid){
        TjianReportVO cidetailedreports = cidetailedreportService.getCidetaildreport(orderid);
        return Result.success(cidetailedreports);
    }
}
