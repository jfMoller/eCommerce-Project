package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponseEntity<Product> addProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    public ResponseEntity<String> deleteProduct(String productId) {
        if (isValidProductID(productId)) {
            productRepository.deleteById(productId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid product ID; delete failed.");
        }
    }

    public ResponseEntity<String> editProduct(String productId, Product editedProduct) {
        if (isValidProductID(productId)) {
            Product product = productRepository.findById(productId).orElse(null);

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

    private boolean isValidProductID(String productId) {
        return productRepository.existsById(productId);
    }
}