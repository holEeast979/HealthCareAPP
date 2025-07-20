package com.example.demo.controller.doctor;

import com.example.demo.pojo.Overallresult;
import com.example.demo.result.Result;
import com.example.demo.service.OverallresultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "总检结论相关接口")
@Slf4j
@RestController
@RequestMapping("doctor/overallresult")
public class OverallresultController {

    @Autowired
    OverallresultService overallresultService;

    @GetMapping("/getAllOverallresult")
    @ApiOperation("获取所有总检结论")
    public Result<List<Overallresult>> getAllOverallresult(String orderid){
        List<Overallresult> overallresults = overallresultService.getAllOverallresult(orderid);
        return Result.success(overallresults);
    }

    @PostMapping("/addOverallresult")
    @ApiOperation("添加总检结论")
    public @ResponseBody Result addOverallresult(@RequestBody Overallresult overallresult){
        overallresultService.addOverallresult(overallresult);
        return Result.success();
    }

    @DeleteMapping("/deleteOverallresult")
    @ApiOperation("删除总检结论")
    public @ResponseBody Result deleteOverallresult( Integer orid){
        overallresultService.deleteOverallresult(orid);
        return Result.success();
    }
}
