package me.code.springboot_neo4j.repositories;

import me.code.springboot_neo4j.models.ProductDetail;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepository extends Neo4jRepository<ProductDetail, String> {

    @Query("MATCH (:Order {id: $orderId})-[:INCLUDES]-(details:ProductDetail) RETURN details")
    Optional<List<ProductDetail>> findDetailsByOrderId(String orderId);

}
