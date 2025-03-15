package sfera.product_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sfera.product_service.dto.ProductRequest;
import sfera.product_service.dto.ProductResponse;
import sfera.product_service.model.Product;
import sfera.product_service.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        return productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(),
                        product.getDescription(), product.getPrice())).toList();
    }
}
