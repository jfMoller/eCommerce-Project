package me.code.springboot_neo4j.repositories;

import me.code.springboot_neo4j.models.ProductDetails;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface ProductDetailsRepository extends Neo4jRepository<ProductDetails, String> {

    @Query("MATCH (:Order {id: $orderId})-[:INCLUDES]-(details:ProductDetails) RETURN details")
    Optional<List<ProductDetails>> findDetailsByOrderId(String orderId);

}
