package me.code.springboot_neo4j.services;

import jakarta.transaction.Transactional;
import me.code.springboot_neo4j.dto.response.entity.OngoingOrder;
import me.code.springboot_neo4j.dto.response.entity.PlacedOrder;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.exceptions.types.UncheckedException;
import me.code.springboot_neo4j.models.ProductDetails;
import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.models.Product;
import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.repositories.OrderRepository;
import me.code.springboot_neo4j.repositories.ProductDetailsRepository;
import me.code.springboot_neo4j.utils.ProductDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final UserAccountService userAccountService;
    private final ProductService productService;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ProductDetailsRepository productDetailsRepository,
            UserAccountService userAccountService,
            ProductService productService) {
        this.orderRepository = orderRepository;
        this.productDetailsRepository = productDetailsRepository;
        this.userAccountService = userAccountService;
        this.productService = productService;
    }

    @Transactional
    public Success placeOrder(String userId, String[] productIds) {
        try {
            User user = userAccountService.loadUserById(userId);
            List<Product> orderedProducts = new ArrayList<>();

            for (String productId : productIds) {
                orderedProducts.add(productService.loadProductById(productId));
            }

            List<ProductDetails> productDetails = ProductDetailsUtil.parseAsProductDetails(orderedProducts);
            orderRepository.save(new Order(user, productDetails));

            return new Success(HttpStatus.OK, "The order was placed successfully");

        } catch (Exception exception) {
            throw new UncheckedException(HttpStatus.BAD_REQUEST, "Could not place order");
        }
    }

    public OngoingOrder getOngoingOrder(String[] productIds) {
        try {
            List<Product> products = productService.getProductsById(productIds);
            double totalPrice = productService.calculateTotalPrice(products);

            return new OngoingOrder(products, totalPrice);

        } catch (Exception exception) {
            throw new UncheckedException(HttpStatus.BAD_REQUEST, "Could not retrieve ongoing entity");
        }
    }

    public List<PlacedOrder> getUsersOrders(String userId) {
        List<Order> orders = findOrdersByUserId(userId);
        List<PlacedOrder> dtos = new ArrayList<>();

        for (var order : orders) {
            var dto = new PlacedOrder(order);
            dtos.add(dto);
        }

        return dtos;
    }

    public List<Order> findOrdersByUserId(String userId) {
        return orderRepository.findOrdersByUserId(userId).orElseThrow(
                () -> new UncheckedException(
                        HttpStatus.NOT_FOUND,
                        "Could not find orders placed by user with id: " + userId));
    }

    public List<ProductDetails> findProductDetailsByOrderId(String orderId) {
        return productDetailsRepository.findDetailsByOrderId(orderId).orElseThrow(
                () -> new UncheckedException(
                        HttpStatus.NOT_FOUND,
                        "Could not find product details for order with id: " + orderId));
    }
}

