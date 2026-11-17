package com.enterprise.orderengine;

import com.enterprise.orderengine.domain.Inventory;
import com.enterprise.orderengine.repository.InventoryRepository;
import com.enterprise.orderengine.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class OrderSagaConcurrencyTest {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() {
        inventoryRepository.deleteAll();
        Inventory inv = new Inventory();
        inv.setSkuCode("LAPTOP-PRO-17");
        inv.setAvailableQuantity(100);
        inventoryRepository.save(inv);
    }

    @Test
    void testConcurrentInventoryReservation() throws InterruptedException {
        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    inventoryService.reserveStock("LAPTOP-PRO-17", 5);
                } catch (Exception e) {
                    // Ignore for test assertion
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        Inventory updated = inventoryRepository.findBySkuCode("LAPTOP-PRO-17").orElseThrow();
        // Should have successfully reserved 50 units across 10 threads (5 each)
        assertEquals(50, updated.getAvailableQuantity());
    }
}
