package com.example.demo.controller.doctor;

import com.example.demo.pojo.Orders;
import com.example.demo.pojo.dto.CireportDTO;
import com.example.demo.pojo.vo.CireportVO;
import com.example.demo.result.Result;
import com.example.demo.service.CidetailedreportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "订单模板相关接口")
@Slf4j
@RestController
@RequestMapping("/doctor")
public class CidetailedreportController {

    @Autowired
    CidetailedreportService cidetailedreportService;


    @PostMapping("/createReportTemplate")
    @ApiOperation("创建订单模板")
    public @ResponseBody Result createReportTemplate(@RequestBody Orders orders) {
        log.info("创建模板的订单：{}", orders);
        cidetailedreportService.createReportTemplate(orders);
        return Result.success();
    }

    @GetMapping("/getReportTemplate")
    @ApiOperation("获取订单细名")
    public Result<List<CireportVO>> getReportTemplate(@RequestParam String orderId) {
        return cidetailedreportService.getReportTemplate(orderId);
    }

    @PostMapping("/updatavValue")
    @ApiOperation("添加体检项值")
    public @ResponseBody Result updatavValue(@RequestBody CireportDTO cireportDTO) {
        cidetailedreportService.updatavValue(cireportDTO);
        return Result.success();
    }

}
