package com.example.demo;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = Product.builder()
                .name("iPhone 13")
                .price(60000.0)
                .build();
    }

    @Test
    void testSaveProduct() {
        Product savedProduct = productService.saveProduct(sampleProduct);

        assertNotNull(savedProduct.getId());
        assertEquals("iPhone 13", savedProduct.getName());
        assertEquals(60000.0, savedProduct.getPrice());
    }

    @Test
    void testGetProductById() {
        Product saved = productService.saveProduct(sampleProduct);

        Product found = productService.getProductById(saved.getId());

        assertEquals(saved.getId(), found.getId());
        assertEquals(saved.getName(), found.getName());
    }

    @Test
    void testGetProductByIdNotFound() {
        Long fakeId = 9999L;

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(fakeId);
        });

        assertTrue(exception.getMessage().contains("Product not found with id"));
    }
}
