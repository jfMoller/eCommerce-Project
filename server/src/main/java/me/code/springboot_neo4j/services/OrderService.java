package me.code.springboot_neo4j.services;

import jakarta.transaction.Transactional;
import me.code.springboot_neo4j.dtos.responses.entities.OngoingOrderDTO;
import me.code.springboot_neo4j.dtos.responses.entities.PlacedOrderDTO;
import me.code.springboot_neo4j.dtos.responses.entities.UnavailableProductDTO;
import me.code.springboot_neo4j.dtos.responses.error.details.OrderErrorDetail;
import me.code.springboot_neo4j.dtos.responses.success.Success;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.exceptions.types.variants.OrderException;
import me.code.springboot_neo4j.models.nodes.Order;
import me.code.springboot_neo4j.models.nodes.OrderItem;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ProductService productService,
            OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    @Transactional
    public Success placeOrder(User user, String[] productIds) {
        try {
            List<Product> products =
                    productService.loadProductsById(productIds);

            List<OrderItem> items =
                    orderItemService.generateOrderItems(products);

            List<UnavailableProductDTO> unavailableProducts =
                    productService.findUnavailableProducts(items);

            if (hasUnavailableProducts(unavailableProducts)) {
                throw new OrderException(
                        HttpStatus.BAD_REQUEST,
                        "Could not place order",
                        new OrderErrorDetail(
                                "The order contains unavailable products",
                                unavailableProducts));
            }

            productService.updateProductQuantities(items);
            orderRepository.save(new Order(user, items));

            return new Success(
                    HttpStatus.OK,
                    "The order was placed successfully");

        } catch (OrderException exception) {
            throw exception;

        } catch (Exception exception) {
            throw new CustomRuntimeException(
                    HttpStatus.BAD_REQUEST,
                    "Could not place order");
        }
    }

    private boolean hasUnavailableProducts(List<UnavailableProductDTO> unavailableProductDTOS) {
        return !unavailableProductDTOS.isEmpty();
    }

    public OngoingOrderDTO getOngoingOrder(String[] productIds) {
        try {
            List<Product> products =
                    productService.loadProductsById(productIds);

            List<OrderItem> items =
                    orderItemService.generateOrderItems(products);

            double totalPrice =
                    orderItemService.getTotalPrice(items);

            return new OngoingOrderDTO(items, totalPrice);

        } catch (Exception exception) {
            throw new CustomRuntimeException(
                    HttpStatus.BAD_REQUEST,
                    "Could not retrieve ongoing order");
        }
    }

    public List<PlacedOrderDTO> getUsersOrders(String userId) {
        return findOrdersByUserId(userId).stream()
                .map(PlacedOrderDTO::new)
                .toList();
    }

    public List<Order> findOrdersByUserId(String userId) {
        return orderRepository.findOrdersByUserId(userId).orElseThrow(
                () -> new CustomRuntimeException(
                        HttpStatus.NOT_FOUND,
                        "Could not find orders placed by user with id: " + userId));
    }

}

