package com.example.order.service;

import com.example.order.dto.OrderItemsDTO;
import com.example.order.dto.OrderResDTO;
import com.example.order.model.*;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
//    private final UserRepository userRepository;

    public Optional<OrderResDTO> createOrder(String userId) {
        //steps will do : validate cart items --> validate user id passed --> calculate total price --> create order --> then empty the cart once order created

        List<CartItems> cartItemsList = cartService.fetchCartItems(userId);
        if(cartItemsList.isEmpty()){
            return Optional.empty();
        }

//        Optional<User> userOptional =  userRepository.findById(Long.valueOf(userId));
//        if(userOptional.isEmpty()){
//            return Optional.empty();
//        }
//
//        User user = userOptional.get();

        String  totalPrice = String.valueOf(cartItemsList.stream()
                .map(item -> new BigDecimal(item.getPrice()))
                .reduce(BigDecimal.ZERO , BigDecimal::add));

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        //cart items needs to convert into order items then create order object to save
        List<OrderItems> orderItems = cartItemsList.stream()
                .map(item -> new OrderItems(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        order,
                        item.getPrice()
                )).toList();

        order.setItemsList(orderItems);
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(userId);

        return Optional.of(mapToOrderRes(savedOrder));

    }

    public OrderResDTO mapToOrderRes(Order order){
        return new OrderResDTO(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItemsList().stream()
                        .map(orderItems -> new OrderItemsDTO(
                                orderItems.getId(),
                                orderItems.getProductId(),
                                orderItems.getQuantity(),
                                orderItems.getPrice()
                        )).toList(),
                order.getCreatedAt()
        );
    }
}
