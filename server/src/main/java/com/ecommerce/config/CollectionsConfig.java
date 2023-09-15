package com.ecommerce.config;

import org.springframework.data.mongodb.core.MongoTemplate;

public class CollectionsConfig {

    private final MongoTemplate mongoTemplate;
    private final String[] collectionNames;

    public CollectionsConfig(MongoTemplate mongoTemplate, String[] collectionNames) {
        this.mongoTemplate = mongoTemplate;
        this.collectionNames = collectionNames;
    }

    public void createCollections() {
        for (String collectionName : collectionNames) {
            if (!mongoTemplate.collectionExists(collectionName)) {
                mongoTemplate.createCollection(collectionName);
                System.out.println("CollectionsConfig created a new collection: " + collectionName);
            }
        }
    }
}
