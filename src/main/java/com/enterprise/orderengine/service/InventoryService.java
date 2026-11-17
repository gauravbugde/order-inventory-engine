package com.enterprise.orderengine.service;

import com.enterprise.orderengine.domain.Inventory;
import com.enterprise.orderengine.exception.InsufficientInventoryException;
import com.enterprise.orderengine.repository.InventoryRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Retryable(retryFor = {OptimisticLockException.class}, maxAttempts = 3, backoff = @Backoff(delay = 100))
    @Transactional
    public void reserveStock(String skuCode, int quantity) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new IllegalArgumentException("SKU not found: " + skuCode));

        if (inventory.getAvailableQuantity() < quantity) {
            throw new InsufficientInventoryException("Insufficient stock for SKU: " + skuCode);
        }

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
        inventoryRepository.save(inventory);
    }
}
