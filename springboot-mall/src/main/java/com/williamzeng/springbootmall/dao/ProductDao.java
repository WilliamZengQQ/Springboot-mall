package com.williamzeng.springbootmall.dao;
import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.dto.ProdustRequest;
public interface ProductDao {

    Product getProductById(Integer productId);
    Integer createProduct(ProdustRequest productRequest);
}
