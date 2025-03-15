package sfera.order_service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;


public interface InventoryClient {

    @GetExchange("/api/inventory/isInStock")
    ResponseEntity<Boolean> isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
}
