package com.example.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemsDTO {
    private Long id;
    private String productId;
    private String quantity;
    private String price;

}
