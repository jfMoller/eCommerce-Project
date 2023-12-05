package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dto.request.EditedProductDTO;
import me.code.springboot_neo4j.dto.request.InsertProductDTO;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.models.Product;
import me.code.springboot_neo4j.security.JwtTokenUtil;
import me.code.springboot_neo4j.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ProductController(ProductService productService, JwtTokenUtil jwtTokenUtil) {
        this.productService = productService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Product>> getFeaturedProducts() {
        return ResponseEntity.ok(productService.getFeaturedProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable String productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @PostMapping("/insert")
    public ResponseEntity<Product> insertProduct(@RequestBody InsertProductDTO dto) {
        var result = productService.insertProduct(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/edit/{productId}")
    public ResponseEntity<Success> editProduct(@PathVariable String productId, @RequestBody EditedProductDTO dto) {
        var result = productService.editProduct(productId, dto);
        return result.toResponseEntity();
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Success> deleteProduct(@PathVariable String productId) {
        var result = productService.deleteProduct(productId);
        return result.toResponseEntity();
    }
}
