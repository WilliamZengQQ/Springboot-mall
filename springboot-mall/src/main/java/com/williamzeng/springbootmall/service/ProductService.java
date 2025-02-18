package com.williamzeng.springbootmall.service;

import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.dto.ProductRequest;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);


}
