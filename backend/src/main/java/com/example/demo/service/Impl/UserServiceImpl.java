package com.example.demo.service.Impl;

import com.example.demo.mapper.UsersMapper;
import com.example.demo.pojo.Users;
import com.example.demo.result.Result;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UsersMapper userMapper;

    public Users login(Users user){
        //其实这里还有一个非空校验，老师今天不校验
        Users userResult=userMapper.selectByPrimaryKey(user.getUserid());
        return userResult;
    }

    public Result<Users> register(Users user) {
        Users userResult=userMapper.selectByPrimaryKey(user.getUserid());
        if(userResult!=null){
            return Result.error("用户已存在");
        }else {
            String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(password);
//            user.setBirthday(LocalDateTime.now());
            userMapper.insert(user);
            return Result.success(user);
        }
    }

    @Override
    public Result<Users> update(Users user) {
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.updateByPrimaryKeySelective(user);
        return Result.success(user);
    }

    public Result<Users> isregister(Users user) {
        Users users = userMapper.selectByPrimaryKey(user.getUserid());
        if(users==null){
            return Result.success();
        }
        return Result.error("用户已存在");
    }
}
