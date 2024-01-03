package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dtos.responses.entities.UnavailableProductDTO;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.models.nodes.OrderItem;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(String productId) {
        try {
            return loadProductById(productId);
        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.NOT_FOUND, "Could not find requested product");
        }
    }

    public List<Product> getFeaturedProducts() {
        int productAmount = 4;
        return productRepository.findProductsWithBiggestQuantity(productAmount);
    }

    public List<Product> getSearchedProducts(String query, String filter) {
        if (isBlankSearchQuery(query)) {
            return loadAllProductsWithFilter(filter);
        } else {
            return loadSearchedProductsWithFilter(query, filter);
        }
    }

    private boolean isBlankSearchQuery(String query) {
        return query.isBlank();
    }

    private List<Product> loadAllProductsWithFilter(String filter) {
        return switch (filter) {
            case "lowest_price" -> productRepository.findAllProductsOrderedByLowestPrice();
            case "highest_price" -> productRepository.findAllProductsOrderedByHighestPrice();
            default -> productRepository.findAll();
        };
    }

    private List<Product> loadSearchedProductsWithFilter(String query, String filter) {
        return switch (filter) {
            case "lowest_price" -> productRepository.findSearchedProductsOrderedByLowestPrice(query);
            case "highest_price" -> productRepository.findSearchedProductsOrderedByHighestPrice(query);
            default -> productRepository.findProductsBySearch(query);
        };
    }

    public List<UnavailableProductDTO> findUnavailableProducts(List<OrderItem> items) {
        List<UnavailableProductDTO> unavailableProducts = new ArrayList<>();

        for (var item : items) {
            Product targetProduct = item.getProduct();
            int requestedAmount = item.getAmount();
            int availableAmount = loadProductById(targetProduct.getId()).getQuantity();

            if (isUnavailableProduct(availableAmount, requestedAmount)) {
                unavailableProducts.add(
                        new UnavailableProductDTO(
                                "Requested amount not available",
                                targetProduct.getId(),
                                requestedAmount,
                                availableAmount));
            }
        }

        return unavailableProducts;
    }

    private boolean isUnavailableProduct(int availableAmount, int requestedAmount) {
        return (availableAmount - requestedAmount) < 0;
    }

    public void updateProductQuantities(List<OrderItem> items) {
        for (var item : items) {
            var targetProduct = item.getProduct();
            targetProduct.setQuantity(targetProduct.getQuantity() - item.getAmount());

            productRepository.save(targetProduct);
        }

    }

    public Product loadProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new CustomRuntimeException(HttpStatus.NOT_FOUND, "Product with id: " + productId + " not found"));
    }

    public List<Product> loadProductsById(String[] productIds) {
        return Stream.of(productIds)
                .map(this::loadProductById)
                .toList();
    }

}
