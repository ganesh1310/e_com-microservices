package com.example.order.clients;

import com.example.order.dto.ProductResDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface productServiceClient {

    @GetExchange("/api/products/getProduct/{id}")
    ProductResDTO getProductDetails(@PathVariable("id") long id);

}
