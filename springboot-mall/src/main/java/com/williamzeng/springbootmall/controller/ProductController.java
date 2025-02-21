package com.williamzeng.springbootmall.controller;

import com.mysql.cj.x.protobuf.Mysqlx;
import com.williamzeng.springbootmall.constant.ProductCategory;
import com.williamzeng.springbootmall.dto.ProductQueryParams;
import com.williamzeng.springbootmall.dto.ProductRequest;
import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) ProductCategory productCategory,
                                                     @RequestParam(required = false) String search,
                                                     @RequestParam(defaultValue = "created_date") String orderBy,
                                                     @RequestParam(defaultValue = "dasc") String sort){ //ResponseEntity裡面是一個裝著Product資訊的list
                                                        //required = false代表是個可選的參數
                                                        //sort決定由小排到大反之亦然
                                                        //defaultValue = "created_date"是為了使用者一點選畫面就為最新的資訊（假設前端沒有傳遞資訊就是預設值created_date）
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setProductCategory(productCategory);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);

        List<Product> productList = productService.getProducts(productQueryParams); //在getProducts當中傳入productCategory的值好讓Dao可以使用Where查詢對應的數值

        return ResponseEntity.status(HttpStatus.OK).body(productList);

    }

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
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId =  productService.createProduct(productRequest); //要從資料庫當中返回ProductId給我們

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){ //額外使用ProductRequest為了限制前端直接修改Product
        //先查詢商品是否存在
        Product product  = productService.getProductById(productId);
        System.out.println("product="+product);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //修改商品數據
        else {
            productService.updatedProduct(productId,productRequest);
            Product updataedproduct = productService.getProductById(productId);
            return ResponseEntity.status(HttpStatus.OK).body(updataedproduct);
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId){

        productService.deleteProductById(productId); //刪除後不用返回什麼數值

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //顯示出數據已經進行刪除

    }
}
