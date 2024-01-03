package me.code.springboot_neo4j.services;

import jakarta.transaction.Transactional;
import me.code.springboot_neo4j.dtos.requests.AddProductDTO;
import me.code.springboot_neo4j.dtos.requests.EditedProductDTO;
import me.code.springboot_neo4j.dtos.responses.entities.UserOrderDTO;
import me.code.springboot_neo4j.dtos.responses.success.Success;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.models.nodes.Order;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.repositories.OrderRepository;
import me.code.springboot_neo4j.repositories.ProductRepository;
import me.code.springboot_neo4j.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class AdminToolsService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public AdminToolsService(
            UserRepository userRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<UserOrderDTO> getAllUsersOrders() {
        return findAllUsersOrders().stream()
                .map(order -> new UserOrderDTO(order, order.getUser().getEmail()))
                .toList();
    }

    public List<UserOrderDTO> getAllUsersOrders(String status) {
        if (isValidOrderStatus(status)) {
            return findAllUsersOrders(status).stream()
                    .map(order -> new UserOrderDTO(order, order.getUser().getEmail()))
                    .toList();
        } else throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, status + " is invalid");
    }

    public boolean isValidOrderStatus(String status) {
        return status.equalsIgnoreCase(Order.Status.PENDING.toString()) ||
                status.equalsIgnoreCase(Order.Status.SENT.toString());
    }

    public List<Order> findAllUsersOrders() {
        return orderRepository.findAllUsersOrders().orElseThrow(
                () -> new CustomRuntimeException(
                        HttpStatus.NOT_FOUND,
                        "Could not find all users orders"));
    }

    public List<Order> findAllUsersOrders(String status) {
        return orderRepository.findAllUsersOrdersByStatus(status).orElseThrow(
                () -> new CustomRuntimeException(
                        HttpStatus.NOT_FOUND,
                        "Could not find all users orders"));
    }

    @Transactional
    public Success sendOrder(String orderId, String dateAndTime) {
        try {
            Order order = findOrder(orderId);

            LocalDateTime expectedDelivery = generateLocalDateTime(dateAndTime);

            orderRepository.setOrderToSent(order.getId(), Order.Status.SENT, expectedDelivery);

            return new Success(HttpStatus.OK, "Order was successfully sent");

        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    private LocalDateTime generateLocalDateTime(String dateAndTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        return LocalDateTime.parse(dateAndTime, dateTimeFormatter);
    }

    @Transactional
    public Success changeExpectedDelivery(String orderId, String newDateAndTime) {
        try {
            Order order = findOrder(orderId);

            LocalDateTime newExpectedDelivery = generateLocalDateTime(newDateAndTime);

            orderRepository.setExpectedDelivery(order.getId(), newExpectedDelivery);

            return new Success(HttpStatus.OK, "Successfully updated expected delivery");

        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    public Order findOrder(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new CustomRuntimeException(
                        HttpStatus.NOT_FOUND,
                        "Could not find order with id: " + orderId));
    }

    public Product addProduct(AddProductDTO dto) {
        try {
            Product product = new Product(dto.name(), dto.description(), dto.imageUrl(), dto.price(), dto.quantity());
            return productRepository.save(product);
        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not create product");
        }
    }

    public Success deleteProduct(String productId) {
        if (isValidProductID(productId)) {
            productRepository.deleteById(productId);
            return new Success(HttpStatus.OK, "The product was deleted successfully");
        } else {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not delete product");
        }
    }

    private boolean isValidProductID(String product_id) {
        return productRepository.existsById(product_id);
    }

    public Success editProduct(String productId, EditedProductDTO dto) {
        if (isValidProductID(productId)) {
            Product product = loadProductById(productId);

            product.setName(dto.name());
            product.setDescription(dto.description());
            product.setPrice(dto.price());
            product.setQuantity(dto.quantity());

            productRepository.save(product);
            return new Success(HttpStatus.OK, "The product was edited successfully");

        } else throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Failed to edit the product");
    }

    public Product loadProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new CustomRuntimeException(HttpStatus.NOT_FOUND, "Product with id: " + productId + " not found"));
    }

}
