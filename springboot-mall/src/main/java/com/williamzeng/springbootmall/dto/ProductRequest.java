package com.williamzeng.springbootmall.dto;

import com.williamzeng.springbootmall.constant.ProductCategory;
import jakarta.validation.constraints.NotNull;

import java.util.Date;


public class ProductRequest{//下方定義前端要回傳的參數以及處理方法等

    //private Integer ProductId;  因為ProductId是mysql自動定義的毋需前端傳入
    @NotNull
    private String ProductName;
    @NotNull
    private ProductCategory Category;//單純用String宣告Category無法得知商品的分類
    @NotNull
    private String ImageUrl;
    @NotNull
    private Integer Price;
    @NotNull
    private Integer Stock;

    private String Description;
    //private Date CreateDate;
    //private Date LastModifiedDate; //因為為 mysql自動定義的毋需前端傳入

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public ProductCategory getCategory() {
        return Category;
    }

    public void setCategory(ProductCategory category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public Integer getStock() {
        return Stock;
    }

    public void setStock(Integer stock) {
        Stock = stock;
    }
}
