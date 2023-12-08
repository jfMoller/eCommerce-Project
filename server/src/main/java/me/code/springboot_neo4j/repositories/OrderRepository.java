package me.code.springboot_neo4j.repositories;

import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.models.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends Neo4jRepository<Order, String> {
    @Query("MATCH (o:Order) WHERE o.id = $id RETURN o")
    @NotNull Optional<Order> findById(@NotNull String id);

    @Query("MATCH (o:Order)-[:PLACED_BY]->(u:User) WHERE u.id = $userId RETURN o")
    Optional<List<Order>> findOrdersByUserId(String userId);

    @Query("MATCH (p:Product)<-[:CONTAINS]-(o:Order) WHERE o.id = $orderId RETURN p")
    Optional<List<Product>> findProductsByOrderId(String orderId);

    @Query("MATCH (u:User)<-[:PLACED_BY]-(o:Order)-[:CONTAINS]->(p:Product) WHERE u.id = $userId RETURN o, COLLECT(p)")
    Optional<List<Object>> findOrdersWithProductsByUserId(@Param("userId") String userId);

}
