package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dtos.requests.EditedProductDTO;
import me.code.springboot_neo4j.dtos.requests.AddProductDTO;
import me.code.springboot_neo4j.dtos.responses.success.Success;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProducts() {
        var result = productService.getProducts();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Product>> getFeaturedProducts() {
        var result = productService.getFeaturedProducts();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable String productId) {
        var result = productService.getProduct(productId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query, @RequestParam String filter) {
        var result = productService.getSearchedProducts(query, filter);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody AddProductDTO dto) {
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
