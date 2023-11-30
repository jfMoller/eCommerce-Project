package me.code.springboot_neo4j.repositories;

import me.code.springboot_neo4j.models.Order;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends Neo4jRepository<Order, String> {
    @Query("MATCH (o:Order) WHERE o.id = $id RETURN o")
    Optional<Order> findById(String id);

    @Query("MATCH (u:User {id: $userId})-[:PLACED_BY]->(o:Order) RETURN o")
    Optional<List<Order>> findOrdersByUserId(String userId);
}
