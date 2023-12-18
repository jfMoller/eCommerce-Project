package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dto.request.EditedProductDTO;
import me.code.springboot_neo4j.dto.request.InsertProductDTO;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Product> getProductsById(String[] productIds) {
        List<Product> products = new ArrayList<>();

        for (String productId : productIds) {
            Product product = loadProductById(productId);
            products.add(product);
        }
        return products;
    }

    public double calculateTotalPrice(List<Product> products) {
        double price = 0;
        for (Product product : products) {
            price += product.getPrice();
        }
        return Math.round(price * 100.0) / 100.0;
    }

    public List<Product> getFeaturedProducts() {
        int productAmount = 4;
        return productRepository.findProductsWithBiggestQuantity(productAmount);
    }

    public Product insertProduct(InsertProductDTO dto) {
        try {
            Product product = new Product(dto.name(), dto.description(), dto.imageUrl(), dto.price(), dto.quantity());
            return productRepository.save(product);
        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not create product");
        }
    }

    public Success deleteProduct(String productId) {
        if (isValidProductID(productId)) {
            productRepository.deleteById(productId);
            return new Success(HttpStatus.OK, "The product was deleted successfully");
        } else {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not delete product");
        }
    }

    private boolean isValidProductID(String product_id) {
        return productRepository.existsById(product_id);
    }

    public Success editProduct(String productId, EditedProductDTO dto) {
        if (isValidProductID(productId)) {
            Product product = loadProductById(productId);

            product.setName(dto.name());
            product.setPrice(dto.price());
            product.setQuantity(dto.quantity());

            productRepository.save(product);
            return new Success(HttpStatus.OK, "The product was edited successfully");

        } else throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Failed to edit the product");
    }

    public Product loadProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new CustomRuntimeException(HttpStatus.NOT_FOUND, "Product with id: " + productId + " not found"));
    }

}
