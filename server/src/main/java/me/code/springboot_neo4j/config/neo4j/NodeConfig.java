package me.code.springboot_neo4j.config.neo4j;

import me.code.springboot_neo4j.repositories.ProductRepository;
import me.code.springboot_neo4j.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NodeConfig implements CommandLineRunner {

    private final UsersConfig usersConfig;
    private final ProductsConfig productsConfig;

    @Autowired
    public NodeConfig(UserRepository userRepository, ProductRepository productRepository) {

        // Handles creation of default users
        this.usersConfig = new UsersConfig(userRepository);

        // Handles creation of default products
        this.productsConfig = new ProductsConfig(productRepository);
    }

    @Override
    public void run(String... args) {
        usersConfig.createDefaultUsers();
        productsConfig.createDefaultProducts();
    }

}
