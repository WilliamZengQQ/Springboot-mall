package com.williamzeng.springbootmall.controller;


import com.williamzeng.springbootmall.dto.UserRegisterRequest;
import com.williamzeng.springbootmall.model.User;
import com.williamzeng.springbootmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //註冊新帳號，在user table創建（post）一筆新的user數據出來
    /*register方法選擇POST的兩個理由
      1.RESTful設計原則中，創建資源對應到POST方法
      2.資安考量，需要使用requst body傳遞參數
     */
    @PostMapping("/Users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {

        Integer userId = userService.register(userRegisterRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user); //被創建出來最後user回傳給前端。
    }

}
