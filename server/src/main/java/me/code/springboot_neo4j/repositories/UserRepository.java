package me.code.springboot_neo4j.repositories;

import me.code.springboot_neo4j.models.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, String> {

    @Query("MATCH (u:User) WHERE u.id = $id RETURN u")
    @NotNull Optional<User> findById(@NotNull String id);

    @Query("MATCH (u:User) WHERE u.email = $email RETURN u")
    Optional<User> findByEmail(String email);

    @Query("MATCH (u:User) WHERE u.username = $username RETURN u")
    Optional<User> findByUsername(String username);

    @Query("MATCH (u:User) WHERE u.email = $email RETURN COUNT(u) > 0")
    boolean isExistingEmail(String email);

    @Query("MATCH (u:User) WHERE u.username = $username RETURN COUNT(u) > 0")
    boolean isExistingUsername(String username);

    @Query("MATCH (u:User) WHERE u.email = $email RETURN COUNT(u) = 0")
    boolean isInvalidEmail(String email);

    @Query("MATCH (u:User) WHERE u.email = $email AND u.password = $password RETURN COUNT(u) = 0")
    boolean isInvalidPassword(String email, String password);

    @Query("MATCH (u:User) WHERE u.id = $userId AND u.email = $email RETURN COUNT(u) > 0")
    boolean isUsersCurrentEmail(String userId, String email);

    @Query("MATCH (u:User) WHERE u.id = $userId AND u.password = $password RETURN COUNT(u) > 0")
    boolean isUsersCurrentPassword(String userId, String password);

    @Query("MATCH (u:User) WHERE u.email = $email AND u.password = $password RETURN COUNT(u) > 0")
    boolean isValidCredentials(String email, String password);
}

