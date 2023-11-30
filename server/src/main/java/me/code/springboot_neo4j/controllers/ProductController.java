package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dtos.EditedProductDTO;
import me.code.springboot_neo4j.dtos.InsertProductDTO;
import me.code.springboot_neo4j.models.Product;
import me.code.springboot_neo4j.services.ProductService;
import me.code.springboot_neo4j.utils.JwtTokenUtil;
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
        return productService.getProducts();
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Product>> getFeaturedProducts() {
        return productService.getFeaturedProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable String productId) {
        return productService.getProduct(productId);
    }

    @PostMapping("/insert")
    public ResponseEntity<Product> insertProduct(@RequestHeader("Authorization") String token, @RequestBody InsertProductDTO dto) {
        var result = productService.addProduct(dto);
        return result;
    }

    @PutMapping("/edit/{productid}")
    public ResponseEntity<String> editProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable String productId,
            @RequestBody EditedProductDTO dto) {
        var adminId = jwtTokenUtil.getTokenId(token);
        var result = productService.editProduct(productId, dto);
        return ResponseEntity.ok("Edited");
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Object> deleteProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable String productId) {
        var result = productService.deleteProduct(productId);
        return ResponseEntity.ok(result);
    }
}
