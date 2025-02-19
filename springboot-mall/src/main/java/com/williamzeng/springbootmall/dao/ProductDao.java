package com.williamzeng.springbootmall.dao;
import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.dto.ProductRequest;
public interface ProductDao {

    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updatedProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
