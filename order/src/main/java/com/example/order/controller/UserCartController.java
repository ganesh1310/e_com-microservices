package com.example.order.controller;

import com.example.order.dto.CartItemRequest;
import com.example.order.model.CartItems;
import com.example.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class UserCartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-USER-ID") String userId,
            @RequestBody CartItemRequest cartItemRequest){

        if(!cartService.addToCart(userId, cartItemRequest)){
            return ResponseEntity.badRequest().body("Product not found or insufficient stock or user not found");
        }else{
            return ResponseEntity.ok("Product added to cart successfully");
        }
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-USER-ID") String userId,
            @PathVariable String productId){
        boolean removed = cartService.removeFromCart(userId, productId);
        if(removed){
            return ResponseEntity.ok("Product removed from cart successfully");
        }else{
            return ResponseEntity.badRequest().body("Product not found in cart or user not found");
        }
    }

    @GetMapping("/fetchCart")
    public ResponseEntity<List<CartItems>> fetchCartItems(@RequestHeader("X-USER-ID") String userId){
        List<CartItems> cartItems = cartService.fetchCartItems(userId);
        if(cartItems.isEmpty()){
            return ResponseEntity.noContent().build(); //no items in cart
        }else{
            return ResponseEntity.ok(cartItems);
        }
    }
}
