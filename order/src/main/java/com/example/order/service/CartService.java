package com.example.order.service;

import com.example.order.dto.CartItemRequest;
import com.example.order.dto.userResponseDTO;
import com.example.order.model.CartItems;
import com.example.order.repository.CartRepository;
import com.example.order.clients.productServiceClient;
import com.example.order.clients.userServiceClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional //ensures that all operations within the service are executed in a single transaction, providing data integrity and consistency. If any operation fails, the entire transaction will be rolled back, preventing partial updates to the database.
public class CartService {

    private final CartRepository cartRepository;
    private final productServiceClient productServiceClient;
    private final userServiceClient userServiceClient;
//    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {

        //validate with user service communication
        userResponseDTO userRes = userServiceClient.getUserById(Long.parseLong(userId));
        System.out.println(userRes);
        if (userRes == null) {
            return false; // user not found
        }

        //validate with product microservice communication
        com.example.order.dto.ProductResDTO productRes = productServiceClient.getProductDetails(Long.parseLong(cartItemRequest.getProductId()));
        if(productRes == null){
            return false;
        }

        if (Integer.parseInt(productRes.getStockQuantity()) < Integer.parseInt(cartItemRequest.getQuantity())) {
            return false;
        }


        //check if the product is already in user's cart
        CartItems existingCartItem = cartRepository.findByUserIdAndProductId(userId, cartItemRequest.getProductId());
        if(existingCartItem != null){ //update quantity and price
            existingCartItem.setQuantity(String.valueOf(Long.parseLong(existingCartItem.getQuantity()) + Long.parseLong(cartItemRequest.getQuantity())));
//            existingCartItem.setPrice(String.valueOf(Long.parseLong(product.getPrice()) * Long.parseLong(existingCartItem.getQuantity())));
            existingCartItem.setPrice(String.valueOf(3000.333));
            cartRepository.save(existingCartItem);
        }else{
            CartItems cartItem = new CartItems();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(cartItemRequest.getQuantity());
//            cartItem.setPrice(String.valueOf(Long.parseLong(product.getPrice()) * Long.parseLong(cartItemRequest.getQuantity())));
            cartItem.setPrice(String.valueOf(3000.333));
            cartRepository.save(cartItem);
        }

        return true;

    }

    public boolean removeFromCart(String userId, String productId) {
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if(userOpt.isEmpty()){
//            return false; //user not found
//        }
//
//        User user = userOpt.get();
//
//        Optional<Product> productOpt = productRepository.findById(Long.parseLong(productId));
//        if(productOpt.isEmpty()){
//            return false; //product not found
//        }
//
//        Product product = productOpt.get();
//
//        CartItems cartItem = cartRepository.findByUserAndProduct(user, product);
//        if(cartItem == null){
//            return false; //product not in cart
//        }
//
//        cartRepository.delete(cartItem);
//        return true;
        CartItems cartItems = cartRepository.findByUserIdAndProductId(userId , productId);

        if(cartItems != null){
            cartRepository.delete(cartItems);
            return true;
        }else{
            return false;
        }
    }

    public List<CartItems> fetchCartItems(String userId) {
       return cartRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }
}
