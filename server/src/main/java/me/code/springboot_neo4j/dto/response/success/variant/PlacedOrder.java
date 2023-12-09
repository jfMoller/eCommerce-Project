package me.code.springboot_neo4j.dto.response.success.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.models.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacedOrder {

    @JsonProperty("id")
    String orderId;

    @JsonProperty("user")
    private String userId;

    @JsonProperty("products")
    private List<Map<String, Object>> productsWithAmounts;

    @JsonProperty("price")
    private double price;

    @JsonProperty("status")
    private Order.OrderStatus status;

    @JsonProperty("received")
    private LocalDateTime received;

    @JsonProperty("expectedDelivery")
    private LocalDateTime expectedDelivery;

    public PlacedOrder(Order order) {
        this.orderId = order.getId();
        this.userId = order.getUser().getId();
        this.productsWithAmounts = parseAsProductWithAmount(order.getProducts());
        this.price = order.getPrice();
        this.status = order.getStatus();
        this.received = order.getReceived();
        this.expectedDelivery = order.getExpectedDelivery();
    }

    public static List<Map<String, Object>> parseAsProductWithAmount(List<Product> products) {
        List<Map<String, Object>> productsWithAmounts = new ArrayList<>();

        for (Product product : products) {
            boolean isMatchingProduct = false;

            // Loop through the existing products ( with amounts )
            for (Map<String, Object> productWithAmount : productsWithAmounts) {
                Product existingProduct = (Product) productWithAmount.get("product");

                // Check if the product match
                if (existingProduct.getId().equals(product.getId())) {
                    int currentAmount = (int) productWithAmount.get("amount");
                    productWithAmount.put("amount", currentAmount + 1); // Increment the amount

                    productWithAmount.put("groupPrice", (product.getPrice() * (int) productWithAmount.get("amount"))); // Increment the price based on amount

                    isMatchingProduct = true;
                    break; // Parsing complete
                }
            }

            // If a product is unique in an order
            if (!isMatchingProduct) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("amount", 1);
                obj.put("product", product);
                obj.put("groupPrice", product.getPrice());
                productsWithAmounts.add(obj);
            }
        }

        return productsWithAmounts;
    }
}
