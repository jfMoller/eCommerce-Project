package me.code.springboot_neo4j.repositories;

import me.code.springboot_neo4j.models.nodes.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends Neo4jRepository<Order, String> {

    @Query("MATCH (o:Order) WHERE o.id = $id RETURN o")
    @NotNull Optional<Order> findById(@NotNull String id);

    @Query("MATCH (o:Order) WHERE o.id = $orderId SET o.expectedDelivery = $expectedDelivery")
    void setExpectedDelivery(@NotNull String orderId, LocalDateTime expectedDelivery);

    @Query("MATCH (o:Order) WHERE o.id = $orderId SET o.status = $status, o.expectedDelivery = $expectedDelivery")
    void setOrderToSent(@NotNull String orderId, Order.Status status, LocalDateTime expectedDelivery);

    // Returns a populated list of the specific User's orders
    @Query("MATCH (user:User {id: $userId})<-[pb:PLACED_BY]-(order:Order)-[includes:INCLUDES]->(item:OrderItem)-[refersTo:REFERS_TO]->(product:Product) " +
            "WITH order, COLLECT(includes) AS includes, collect(item) AS items, COLLECT(refersTo) AS refersTo, COLLECT(product) AS products " +
            "RETURN order, includes, items, refersTo, products")
    Optional<List<Order>> findOrdersByUserId(String userId);

    // Returns a populated list of all Users orders
    @Query("MATCH (user:User)<-[placedBy:PLACED_BY]-(order:Order)-[includes:INCLUDES]->(item:OrderItem)-[refersTo:REFERS_TO]->(product:Product) " +
            "WITH order, placedBy, user, COLLECT(includes) AS includes, collect(item) AS items, COLLECT(refersTo) AS refersTo, COLLECT(product) AS products " +
            "RETURN order, placedBy, user, includes, items, refersTo, products")
    Optional<List<Order>> findAllUsersOrders();

    // Returns a populated list of all Users orders with a specific order status
    @Query("MATCH (user:User)<-[placedBy:PLACED_BY]-(order:Order)-[includes:INCLUDES]->(item:OrderItem)-[refersTo:REFERS_TO]->(product:Product) " +
            "WHERE order.status = $status " +
            "WITH order, placedBy, user, COLLECT(includes) AS includes, collect(item) AS items, COLLECT(refersTo) AS refersTo, COLLECT(product) AS products " +
            "RETURN order, placedBy, user, includes, items, refersTo, products")
    Optional<List<Order>> findAllUsersOrdersByStatus(String status);

}
