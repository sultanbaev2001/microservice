package sfera.product_service.dto;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, String description, Double price) {
}
