package com.ecommerce.service.product;

import com.ecommerce.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductServiceInterface {

    ResponseEntity<List<Product>> getProducts();

    ResponseEntity<Object> getProduct(String product_id);

    ResponseEntity<Product> addProduct(Product product);

    ResponseEntity<String> deleteProduct(String product_id);

    ResponseEntity<String> editProduct(String product_id, Product editedProduct);
}