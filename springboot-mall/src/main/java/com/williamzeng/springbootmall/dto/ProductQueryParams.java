package com.williamzeng.springbootmall.dto;

import com.williamzeng.springbootmall.constant.ProductCategory;

public class ProductQueryParams {

    ProductCategory productCategory;
    String search;

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
