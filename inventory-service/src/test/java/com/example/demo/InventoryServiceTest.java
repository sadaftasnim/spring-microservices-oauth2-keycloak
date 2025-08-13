package com.example.demo;

import com.example.demo.model.Inventory;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.service.InventoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsInStock_WhenQuantityGreaterThanZero_ShouldReturnTrue() {
        Inventory inventory = Inventory.builder()
                .id(1L)
                .skuCode("iPhone13")
                .quantity(5)
                .build();

        when(inventoryRepository.findBySkuCode("iPhone13"))
                .thenReturn(Optional.of(inventory));

        boolean result = inventoryService.isInStock("iPhone13");
        assertTrue(result);
    }

    @Test
    void testIsInStock_WhenQuantityZero_ShouldReturnFalse() {
        Inventory inventory = Inventory.builder()
                .id(2L)
                .skuCode("SamsungS23")
                .quantity(0)
                .build();

        when(inventoryRepository.findBySkuCode("SamsungS23"))
                .thenReturn(Optional.of(inventory));

        boolean result = inventoryService.isInStock("SamsungS23");
        assertFalse(result);
    }

    @Test
    void testIsInStock_WhenInventoryNotFound_ShouldReturnFalse() {
        when(inventoryRepository.findBySkuCode("NonExistent"))
                .thenReturn(Optional.empty());

        boolean result = inventoryService.isInStock("NonExistent");
        assertFalse(result);
    }
}
