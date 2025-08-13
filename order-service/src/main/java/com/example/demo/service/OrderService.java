package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public String placeOrder(Order order) {
        Boolean inStock = restTemplate.getForObject(
                "http://localhost:8082/api/inventory/" + order.getSkuCode(), Boolean.class);

        if (Boolean.TRUE.equals(inStock)) {
            orderRepository.save(order);
            return "Order placed successfully!";
        } else {
            return "Product not in stock.";
        }
    }
}
