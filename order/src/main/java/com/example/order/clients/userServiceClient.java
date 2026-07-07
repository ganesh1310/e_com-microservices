package com.example.order.clients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import com.example.order.dto.userResponseDTO;

@HttpExchange
public interface userServiceClient {

    @GetExchange("/api/users/getUserById/{id}")
    userResponseDTO getUserById(@PathVariable("id") long id);
}
