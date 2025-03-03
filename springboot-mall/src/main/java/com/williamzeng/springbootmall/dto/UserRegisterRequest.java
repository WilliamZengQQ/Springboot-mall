package com.williamzeng.springbootmall.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRegisterRequest {

    @NotBlank //表示該參數不能是空白和NULL
    //去接住使用者前端回傳的email & password
    private String email;
    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
