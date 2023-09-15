package com.ecommerce.config;

import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoDBConfig implements CommandLineRunner {

    private final CollectionsConfig collectionsConfig;
    private final UsersConfig usersConfig;
    private final ProductsConfig productsConfig;

    @Autowired
    public MongoDBConfig(MongoTemplate mongoTemplate, UserRepository userRepository, ProductRepository productRepository) {
        // Create the following collections on startup (if they do not already exist)
        String[] collections = new String[]{"users", "orders", "products"};

        // Handles creation of collections
        this.collectionsConfig = new CollectionsConfig(mongoTemplate, collections);

        // Handles creation of default users
        this.usersConfig = new UsersConfig(userRepository);

        // Handles creation of default products
        this.productsConfig = new ProductsConfig(productRepository);
    }

    @Override
    public void run(String... args) {
        collectionsConfig.createCollections();
        usersConfig.createDefaultUsers();
        productsConfig.createDefaultProducts();
    }
}
