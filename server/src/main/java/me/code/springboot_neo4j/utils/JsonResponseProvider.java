package me.code.springboot_neo4j.utils;

import me.code.springboot_neo4j.dtos.ResponseStatusDTO;
import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.models.Product;
import me.code.springboot_neo4j.models.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.code.springboot_neo4j.dtos.ResponseStatusDTO.SUCCESS;

public class JsonResponseProvider {

    public static ResponseEntity<Object> sendAuthenticationEntity(UserRole role, String token) {
        Map<String, Object> response = new HashMap<>();
        response.put(SUCCESS.toString(), true);
        response.put("userRole", role);
        response.put("token", token);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static ResponseEntity<Object> sendUserDetailsEntity(String username, String email) {
        Map<String, Object> response = new HashMap<>();
        response.put(SUCCESS.toString(), true);
        response.put("username", username);
        response.put("email", email);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static ResponseEntity<Object> sendResponseEntity(
            ResponseStatusDTO statusDTO, HttpStatus httpStatus, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put(statusDTO.toString(), true);
        response.put("message", message);

        return ResponseEntity.status(httpStatus).body(response);
    }

    public static Map<String, Object> sendUserOrderJson(Order order) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", order.getId());
        response.put("received", order.getReceived());
        response.put("status", order.getStatus());
        response.put("expectedDelivery", order.getExpectedDelivery());
        response.put("price", order.getPrice());
        response.put("products", parseAsProductWithAmount(order.getProducts()));

        return response;
    }

    public static Map<String, Object> sendOngoingOrderJson(List<Product> requestedProducts, double totalPrice) {
        Map<String, Object> response = new HashMap<>();
        response.put("products", parseAsProductWithAmount(requestedProducts));
        response.put("totalPrice", totalPrice);

        return response;
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
