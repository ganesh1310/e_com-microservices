package com.example.order.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "CART_ITEMS_TABLE")
@Data
@NoArgsConstructor
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne //single user can have multiple cart items
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne //single product can be in multiple cart items
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;

    private String userId;
    private String productId;

    private String quantity;
    private String price;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
