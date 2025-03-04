package com.williamzeng.springbootmall.dao;

import com.williamzeng.springbootmall.dto.UserRegisterRequest;
import com.williamzeng.springbootmall.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    User getByEmail(String email);
}
