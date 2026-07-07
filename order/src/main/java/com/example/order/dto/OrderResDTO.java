package com.example.order.dto;

import com.example.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResDTO {
    private long id;
    private String totalAmount;
    private OrderStatus status;
    private List<OrderItemsDTO> items;
    private LocalDateTime createdAt;
}
