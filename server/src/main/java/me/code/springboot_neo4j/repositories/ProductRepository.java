package me.code.springboot_neo4j.repositories;

import me.code.springboot_neo4j.models.nodes.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends Neo4jRepository<Product, String> {

    @Query("MATCH (p:Product) WHERE p.quantity > 0 RETURN p")
    @NotNull List<Product> findAll();

    @Query("MATCH (p:Product) WHERE p.id = $id RETURN p")
    @NotNull Optional<Product> findById(@NotNull String id);

    @Query("MATCH (p:Product) WHERE p.quantity > 0 return p ORDER BY p.quantity DESC LIMIT $productAmount")
    @NotNull List<Product> findProductsWithBiggestQuantity(int productAmount);


}
