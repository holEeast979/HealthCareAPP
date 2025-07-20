package com.example.demo.service;
import com.example.demo.pojo.Users;
import com.example.demo.result.Result;

public interface UserService {
    Users login(Users user);

    Result<Users> register(Users user);

    Result<Users> update(Users user);

    Result<Users> isregister(Users user);
//    void login(Users user);
}
