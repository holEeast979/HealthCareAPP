package com.example.demo.service;

import com.example.demo.pojo.Overallresult;

import java.util.List;

public interface OverallresultService {
    List<Overallresult> getAllOverallresult(String orderid);

    int addOverallresult(Overallresult overallresult);

    int deleteOverallresult(Integer orid);
}
