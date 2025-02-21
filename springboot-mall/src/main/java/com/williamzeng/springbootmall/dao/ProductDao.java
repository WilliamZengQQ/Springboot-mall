package com.williamzeng.springbootmall.dao;
import com.williamzeng.springbootmall.constant.ProductCategory;
import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.dto.ProductRequest;
import java.util.List;
public interface ProductDao {


    List<Product> getProducts(ProductCategory productCategory,String search);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updatedProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
