package me.code.springboot_neo4j.services;

import jakarta.transaction.Transactional;
import me.code.springboot_neo4j.dto.response.entity.OngoingOrder;
import me.code.springboot_neo4j.dto.response.entity.PlacedOrder;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.exceptions.types.UncheckedException;
import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.models.Product;
import me.code.springboot_neo4j.models.ProductDetail;
import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.models.objects.OrderDTO;
import me.code.springboot_neo4j.repositories.OrderRepository;
import me.code.springboot_neo4j.repositories.ProductDetailRepository;
import me.code.springboot_neo4j.repositories.UserRepository;
import me.code.springboot_neo4j.utils.ProductDetailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            UserRepository userRepository,
            ProductDetailRepository productDetailRepository,
            UserAccountService userAccountService,
            ProductService productService) {
        this.orderRepository = orderRepository;
        this.productDetailRepository = productDetailRepository;
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

            List<ProductDetail> productDetails = ProductDetailUtil.parseAsProductDetails(orderedProducts);
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
        List<OrderDTO> orders = findOrdersByUserId(userId);
        List<PlacedOrder> dtos = new ArrayList<>();

        for (var order : orders) {
            dtos.add(new PlacedOrder(order.getOrder(), order.getDetails()));
        }
        return dtos;
    }

    public List<OrderDTO> findOrdersByUserId(String userId) {
        return orderRepository.findOrdersByUserId(userId).orElseThrow(
                () -> new UncheckedException(
                        HttpStatus.NOT_FOUND,
                        "Could not find orders placed by user with id: " + userId));
    }

    public List<ProductDetail> findProductDetailsByOrderId(String orderId) {
        return productDetailRepository.findDetailsByOrderId(orderId).orElseThrow(
                () -> new UncheckedException(
                        HttpStatus.NOT_FOUND,
                        "Could not find product details for order with id: " + orderId));
    }
}

