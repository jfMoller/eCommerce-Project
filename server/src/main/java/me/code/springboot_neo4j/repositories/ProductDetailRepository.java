package me.code.springboot_neo4j.repositories;

import me.code.springboot_neo4j.models.nodes.ProductDetail;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ProductDetailRepository extends Neo4jRepository<ProductDetail, String> {
}
