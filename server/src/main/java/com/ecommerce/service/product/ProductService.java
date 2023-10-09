package com.ecommerce.service.product;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<Object> getProduct(String product_id) {
        Optional<Product> requestedProduct = productRepository.findById(product_id);

        if (requestedProduct.isPresent()) {
            return ResponseEntity.ok(requestedProduct.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Requested product_id: \n" +
                            product_id + "\n" +
                            "Result: \n" +
                            "Product does not exist; retrieval failed.");
        }
    }

    public ResponseEntity<List<Product>> getFeaturedProducts() {
        List<Product> allProducts = productRepository.findAll();

        List<Product> sortedProducts = allProducts.stream()
                .sorted(Comparator.comparingInt(Product::getQuantity).reversed())
                .toList();

        List<Product> topFourProducts = sortedProducts.stream()
                .limit(4)
                .toList();
        return ResponseEntity.ok(topFourProducts);
    }

    public ResponseEntity<Product> addProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    public ResponseEntity<String> deleteProduct(String product_id) {
        if (isValidProductID(product_id)) {
            productRepository.deleteById(product_id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid product ID; delete failed.");
        }
    }

    public ResponseEntity<String> editProduct(String product_id, Product editedProduct) {
        if (isValidProductID(product_id)) {
            Product product = productRepository.findById(product_id).orElse(null);

            if (product != null) {
                product.setName(editedProduct.getName());
                product.setPrice(editedProduct.getPrice());
                product.setQuantity(editedProduct.getQuantity());

                productRepository.save(product);
                return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully.");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid product ID; update failed.");
    }

    private boolean isValidProductID(String product_id) {
        return productRepository.existsById(product_id);
    }
}