package me.code.springboot_neo4j.services;

import jakarta.transaction.Transactional;
import me.code.springboot_neo4j.dto.response.entity.OngoingOrder;
import me.code.springboot_neo4j.dto.response.entity.PlacedOrder;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.models.nodes.Order;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.models.nodes.ProductDetail;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.repositories.OrderRepository;
import me.code.springboot_neo4j.repositories.ProductDetailRepository;
import me.code.springboot_neo4j.utils.ProductDetailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductDetailRepository productDetailRepository;
    private final UserAccountService userAccountService;
    private final ProductService productService;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ProductDetailRepository productDetailRepository,
            UserAccountService userAccountService,
            ProductService productService) {
        this.orderRepository = orderRepository;
        this.productDetailRepository = productDetailRepository;
        this.userAccountService = userAccountService;
        this.productService = productService;
    }

    @Transactional
    public Success placeOrder(User user, String[] productIds) {
        try {
            List<Product> orderedProducts = Arrays.stream(productIds)
                    .map(productService::loadProductById)
                    .toList();

            List<ProductDetail> productDetails = ProductDetailUtil.parseAsProductDetails(orderedProducts);
            orderRepository.save(new Order(user, productDetails));

            return new Success(HttpStatus.OK, "The order was placed successfully");

        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not place order");
        }
    }

    public OngoingOrder getOngoingOrder(String[] productIds) {
        try {
            List<Product> products = productService.getProductsById(productIds);
            double totalPrice = productService.calculateTotalPrice(products);

            return new OngoingOrder(products, totalPrice);

        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not retrieve ongoing entity");
        }
    }

    public List<PlacedOrder> getUsersOrders(String userId) {
        List<Order> orders = findOrdersByUserId(userId);

        return orders.stream()
                .map(PlacedOrder::new)
                .toList();
    }

    public List<Order> findOrdersByUserId(String userId) {
        return orderRepository.findOrdersByUserId(userId).orElseThrow(
                () -> new CustomRuntimeException(
                        HttpStatus.NOT_FOUND,
                        "Could not find orders placed by user with id: " + userId));
    }

}

