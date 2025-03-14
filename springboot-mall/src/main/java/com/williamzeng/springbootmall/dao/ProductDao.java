package com.williamzeng.springbootmall.dao;

import com.williamzeng.springbootmall.dto.ProductQueryParams;
import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.dto.ProductRequest;
import java.util.List;
public interface ProductDao {


    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updatedProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
    Integer countProducts(ProductQueryParams productQueryParams);
    void updateStock(Integer productId,Integer stock);
}
