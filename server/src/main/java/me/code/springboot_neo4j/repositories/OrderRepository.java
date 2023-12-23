package me.code.springboot_neo4j.repositories;

import me.code.springboot_neo4j.models.nodes.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends Neo4jRepository<Order, String> {

    @Query("MATCH (o:Order) WHERE o.id = $id RETURN o")
    @NotNull Optional<Order> findById(@NotNull String id);

    // Returns a populated list of the User's orders
    @Query("MATCH (user:User {id: $userId})<-[pb:PLACED_BY]-(order:Order)-[includes:INCLUDES]->(item:OrderItem)-[refersTo:REFERS_TO]->(product:Product) " +
            "WITH order, COLLECT(includes) AS includes, collect(item) AS items, COLLECT(refersTo) AS refersTo, collect(product) AS products " +
            "RETURN order, includes, items, refersTo, products")
    Optional<List<Order>> findOrdersByUserId(String userId);

}
