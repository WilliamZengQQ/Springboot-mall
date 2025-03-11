package com.williamzeng.springbootmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateOrderRequest {

    /*
        buyItemList
        那去對應到前端所傳過來的 key 的值
        那因為在這個 buyItemList 裡面
        他存放的是另一個 json object
        那所以我們必須要再去創建一個 Java class
        去對應到這個 json object 的格式
        那所以我一樣是在 dto 這個 package 底下
        再去創建一個 class 出來
        那名字我就叫 BuyItem
     */
    @NotEmpty //驗證List當中不可以是空的
    private List<ByItem> byItemList; //這將前端所回傳的json轉換成Java Object

    public List<ByItem> getByItemList() {
        return byItemList;
    }

    public void setByItemList(List<ByItem> byItemList) {
        this.byItemList = byItemList;
    }
}
