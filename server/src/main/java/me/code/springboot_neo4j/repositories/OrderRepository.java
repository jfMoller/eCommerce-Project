package me.code.springboot_neo4j.repositories;

import me.code.springboot_neo4j.models.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends Neo4jRepository<Order, String> {

    @Query("MATCH (o:Order) WHERE o.id = $id RETURN o")
    @NotNull Optional<Order> findById(@NotNull String id);

    @Query("MATCH (user:User {id: $userId})<-[pb:PLACED_BY]-(order:Order)-[includes:INCLUDES]->(d:ProductDetail)-[refersTo:REFERS_TO]->(p:Product) " +
            "WITH order, COLLECT(includes) AS includes, collect(d) AS details, COLLECT(refersTo) AS refersTo, collect(p) AS products " +
            "RETURN order, includes, details, refersTo, products")
    Optional<List<Order>> findOrdersByUserId(String userId);

}
