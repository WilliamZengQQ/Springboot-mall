package com.williamzeng.springbootmall.dao;
import com.williamzeng.springbootmall.model.Product;
public interface ProductDao {

    Product getProductById(Integer productId);
}
