package com.example.order.dto;

import lombok.Data;

@Data
public class ProductResDTO {
    private String name;
    private String description;
    private String price;
    private String category;
    private String stockQuantity;
    private String imageUrl;
    private boolean active = true;
}
