package com.ecommerce.controller;


import com.ecommerce.auth.JwtAuthProvider;
import com.ecommerce.entity.Product;
import com.ecommerce.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    private final JwtAuthProvider jwtAuthProvider;

    private final boolean requiresAdminRole = true;

    @Autowired
    public ProductController(ProductService productService, JwtAuthProvider jwtAuthProvider) {
        this.productService = productService;
        this.jwtAuthProvider = jwtAuthProvider;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<Object> getProduct(@PathVariable String product_id) {
        return productService.getProduct(product_id);
    }

    @PostMapping("/insert")
    public ResponseEntity<Product> insertProduct(
            @RequestHeader("Authorization") String token, @RequestBody Product product) {
        return jwtAuthProvider.authorizeAccess(
                requiresAdminRole, token, () -> productService.addProduct(product));
    }

    @PutMapping("/edit/{product_id}")
    public ResponseEntity<String> editProduct(
            @RequestHeader("Authorization") String token, @PathVariable String product_id, @RequestBody Product editedProduct) {
        return jwtAuthProvider.authorizeAccess(
                requiresAdminRole, token, () -> productService.editProduct(product_id, editedProduct));
    }

    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<String> deleteProduct(
            @RequestHeader("Authorization") String token, @PathVariable String product_id) {
        return jwtAuthProvider.authorizeAccess(
                requiresAdminRole, token, () -> productService.deleteProduct(product_id));
    }
}
