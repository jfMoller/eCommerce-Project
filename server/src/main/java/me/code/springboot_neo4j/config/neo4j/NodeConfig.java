package me.code.springboot_neo4j.config.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NodeConfig implements CommandLineRunner {

    private final UsersConfig usersConfig;
    private final ProductsConfig productsConfig;

    @Autowired
    public NodeConfig(UsersConfig usersConfig, ProductsConfig productsConfig) {
        this.usersConfig = usersConfig;
        this.productsConfig = productsConfig;
    }

    @Override
    public void run(String... args) {
        usersConfig.createDefaultUsers();
        productsConfig.createDefaultProducts();
    }

}
