package com.example.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne //multiple orders can link to single product
//    @JoinColumn(name = "product_id" , nullable = false)
//    private Product product;
    private String productId;

    private String quantity;

    @ManyToOne //multiple order items can be in single order
    @JoinColumn(name = "order_id" , nullable = false)
    private Order order;

    private String price;
}
