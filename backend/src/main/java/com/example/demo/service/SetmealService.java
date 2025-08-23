package com.example.demo.service;

import com.example.demo.pojo.Setmeal;
import com.example.demo.pojo.Users;

import java.util.List;

public interface SetmealService {

    List<Setmeal> getSetmeal(Users users);

    Setmeal findbysmId(Integer smId);

    Setmeal getSetmealBysmid(Integer smid);

    List<Setmeal> setmeallist();
}
