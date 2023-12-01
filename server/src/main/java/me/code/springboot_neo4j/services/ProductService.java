package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dto.request.EditedProductDTO;
import me.code.springboot_neo4j.dto.request.InsertProductDTO;
import me.code.springboot_neo4j.models.Product;
import me.code.springboot_neo4j.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                    "Product with _id: " + product_id + " could not be found.");
        }
    }

    public Product getProductById(String product_id) {
        Optional<Product> requestedProduct = productRepository.findById(product_id);
        return requestedProduct.orElse(null);
    }

    public List<Product> getProductsById(String[] product_ids) {
        List<Product> requestedProducts = new ArrayList<>();

        for (String product_id : product_ids) {
            Product product = loadProductById(product_id);
            requestedProducts.add(product);
        }
        return requestedProducts;
    }

    public double calculateTotalPrice(List<Product> products) {
        double price = 0;
        for (Product product : products) {
            price += product.getPrice();
        }
        return Math.round(price * 100.0) / 100.0;
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

    public ResponseEntity<Product> addProduct(InsertProductDTO dto) {
        Product product = new Product(dto.name(), dto.description(), dto.imageUrl(), dto.price(), dto.quantity());
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

    public ResponseEntity<String> editProduct(String product_id, EditedProductDTO dto) {
        if (isValidProductID(product_id)) {
            Product product = productRepository.findById(product_id).orElse(null);

            if (product != null) {
                product.setName(dto.name());
                product.setPrice(dto.price());
                product.setQuantity(dto.quantity());

                productRepository.save(product);
                return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully.");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid product ID; update failed.");
    }

    private boolean isValidProductID(String product_id) {
        return productRepository.existsById(product_id);
    }

    public Product loadProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Product with id: " + productId + " not found"));
    }
}
