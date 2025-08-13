package com.example.demo;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);

        sampleOrder = Order.builder()
                .skuCode("iPhone13")
                .quantity(1)
                .build();
    }

    @Test
    void testPlaceOrder_inStock() {
        mockServer.expect(once(),
                        requestTo("http://localhost:8082/api/inventory/iPhone13"))
                .andRespond(withSuccess("true", MediaType.APPLICATION_JSON));

        String result = orderService.placeOrder(sampleOrder);
        assertEquals("Order placed successfully!", result);

        assertTrue(orderRepository.findAll().stream()
                .anyMatch(o -> o.getSkuCode().equals("iPhone13")));
    }

    @Test
    void testPlaceOrder_notInStock() {
        mockServer.expect(once(),
                        requestTo("http://localhost:8082/api/inventory/iPhone13"))
                .andRespond(withSuccess("false", MediaType.APPLICATION_JSON));

        String result = orderService.placeOrder(sampleOrder);
        assertEquals("Product not in stock.", result);
    }
}
