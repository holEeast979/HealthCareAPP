package com.example.demo.service.Impl;

import com.example.demo.mapper.CheckitemMapper;
import com.example.demo.mapper.SetmealMapper;
import com.example.demo.pojo.Checkitem;
import com.example.demo.pojo.Setmeal;
import com.example.demo.pojo.Users;
import com.example.demo.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    CheckitemMapper checkitemMapper;

    public List<Setmeal> getSetmeal(Users users) {
        List<Setmeal> pd =setmealMapper.getBysexSetmel(users);
        for(Setmeal setmeal:pd){
            List<Checkitem> checkitems = checkitemMapper.getCheckitem(setmeal);
            setmeal.setCheckitemList(checkitems);
        }
        return pd;
    }

    public Setmeal findbysmId(Integer smId) {
        return setmealMapper.selectByPrimaryKey(smId);
    }

    @Override
    public Setmeal getSetmealBysmid(Integer smid) {
        return setmealMapper.selectByPrimaryKey(smid);
    }

    public List<Setmeal> setmeallist() {
        return setmealMapper.seteamllist();
    }
}
