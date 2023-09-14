package com.ecommerce.controller;


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

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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
    public ResponseEntity<Product> insertProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/edit/{product_id}")
    public ResponseEntity<String> editProduct(@PathVariable String product_id, @RequestBody Product editedProduct) {
        return productService.editProduct(product_id, editedProduct);
    }

    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String product_id) {
        return productService.deleteProduct(product_id);
    }
}
