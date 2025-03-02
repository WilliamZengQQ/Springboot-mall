package com.williamzeng.springbootmall.service.impl;

import com.williamzeng.springbootmall.dao.UserDao;
import com.williamzeng.springbootmall.dto.UserRegisterRequest;
import com.williamzeng.springbootmall.model.User;
import com.williamzeng.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return  userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
