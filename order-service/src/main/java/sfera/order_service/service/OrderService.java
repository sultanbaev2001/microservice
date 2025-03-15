package sfera.order_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sfera.order_service.client.InventoryClient;
import sfera.order_service.dto.OrderRequest;
import sfera.order_service.model.Order;
import sfera.order_service.order.OrderRepository;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public String placeOrder(OrderRequest orderRequest) {
        ResponseEntity<Boolean> inStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if(Objects.equals(inStock.getBody(), Boolean.TRUE)) {
            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .price(orderRequest.price())
                    .skuCode(orderRequest.skuCode())
                    .quantity(orderRequest.quantity())
                    .build();
            Order savedOrder = orderRepository.save(order);
            log.info("Order Placed: {}", savedOrder.getId());
            return "Order placed successfully";
        }else
            throw new RuntimeException("Product is not in stock");
    }
}
