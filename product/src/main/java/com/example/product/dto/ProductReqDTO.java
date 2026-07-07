package com.example.product.dto;

import lombok.Data;

@Data
public class ProductReqDTO {
    private String name;
    private String description;
    private String price;
    private String category;
    private String stockQuantity;
    private String imageUrl;
}
