package me.code.springboot_neo4j.repositories;

import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.models.objects.OrderDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends Neo4jRepository<Order, String> {
    @Query("MATCH (o:Order) WHERE o.id = $id RETURN o")
    @NotNull Optional<Order> findById(@NotNull String id);

    @Query("MATCH path = (:User {id: $userId})<-[:PLACED_BY]-(order:Order)-[inc:INCLUDES]->(d:ProductDetail)-[rt:REFERS_TO]->(p:Product) " +
            "WITH order, COLLECT(inc) AS rels, collect(d) AS details, COLLECT(rt) AS rels2, collect(p) AS products " +
            "RETURN order, details, products")
    Optional<List<OrderDTO>> findOrdersByUserId(String userId);

}
