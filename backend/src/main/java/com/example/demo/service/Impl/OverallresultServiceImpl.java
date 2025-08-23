package com.example.demo.service.Impl;

import com.example.demo.mapper.OverallresultMapper;
import com.example.demo.pojo.Overallresult;
import com.example.demo.service.OverallresultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OverallresultServiceImpl implements OverallresultService {
    @Autowired
    OverallresultMapper overallresultMapper;

    public List<Overallresult> getAllOverallresult(String orderid) {

        return overallresultMapper.getAllOverallresultList(orderid);
    }

    public int addOverallresult(Overallresult overallresult) {
        return overallresultMapper.insertSelective(overallresult);
    }

    @Override
    public int deleteOverallresult(Integer orid) {
        return overallresultMapper.deleteByPrimaryKey(orid);
    }
}
