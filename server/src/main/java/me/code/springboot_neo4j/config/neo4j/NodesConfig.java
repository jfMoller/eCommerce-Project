package me.code.springboot_neo4j.config.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NodesConfig implements CommandLineRunner {

    private final MockUsersConfig mockUsersConfig;
    private final MockProductsConfig mockProductsConfig;

    @Autowired
    public NodesConfig(MockUsersConfig mockUsersConfig, MockProductsConfig mockProductsConfig) {
        this.mockUsersConfig = mockUsersConfig;
        this.mockProductsConfig = mockProductsConfig;
    }

    @Override
    public void run(String... args) {
        mockUsersConfig.createDefaultUsers();
        mockProductsConfig.createDefaultProducts();
    }

}
