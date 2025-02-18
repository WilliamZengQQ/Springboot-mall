package com.williamzeng.springbootmall.controller;

import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.williamzeng.springbootmall.dto.ProdustRequest;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        //System.out.println("ProductController.getProduct");
        Product product = productService.getProductById(productId);
        //判斷productId是否存在於列表當中，且依照判斷回傳http狀態碼
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid  ProdustRequest productRequest){
        Integer productId =  productService.createProduct(productRequest); //要從資料庫當中返回ProductId給我們

        Product product = productService.getProductById(productId);
        System.out.println("ProductController.createProduct");
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}