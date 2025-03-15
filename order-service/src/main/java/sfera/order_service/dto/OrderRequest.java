package sfera.order_service.dto;

public record OrderRequest(String skuCode, Double price, Integer quantity) {
}
