package sfera.product_service.dto;

import java.math.BigDecimal;

public record ProductRequest(String name, String description, Double price) {
}
