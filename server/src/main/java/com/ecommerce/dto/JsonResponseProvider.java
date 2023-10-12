package com.ecommerce.dto;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ecommerce.dto.ResponseStatus.SUCCESS;

public class JsonResponseProvider {

    public static ResponseEntity<Object> sendAuthenticationEntity(Role userRole, String jwtToken) {
        Map<String, Object> response = new HashMap<>();
        response.put(SUCCESS.toString(), true);
        response.put("userRole", userRole);
        response.put("token", jwtToken);

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
            ResponseStatus responseStatus, HttpStatus httpStatus, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put(responseStatus.toString(), true);
        response.put("message", message);

        return ResponseEntity.status(httpStatus).body(response);
    }

    public static Map<String, Object> sendUserOrderJson(Order order) {
        Map<String, Object> response = new HashMap<>();
        response.put("_id", order.get_id());
        response.put("received", order.getReceived());
        response.put("status", order.getStatus());
        response.put("expectedDelivery", order.getExpectedDelivery());
        response.put("price", order.getPrice());
        response.put("products", parseAsProductWithAmount(order.getProducts()));

        return response;
    }

    private static List<Map<String, Object>> parseAsProductWithAmount(List<Product> products) {
        List<Map<String, Object>> productsWithAmounts = new ArrayList<>();

        for (Product product : products) {
            boolean isMatchingProduct = false;

            // Loop through the existing products ( with amounts )
            for (Map<String, Object> productWithAmount : productsWithAmounts) {
                Product existingProduct = (Product) productWithAmount.get("product");

                // Check if the product match
                if (existingProduct.get_id().equals(product.get_id())) {
                    int currentAmount = (int) productWithAmount.get("amount");
                    productWithAmount.put("amount", currentAmount + 1); // Increment the amount
                    isMatchingProduct = true;
                    break; // Parsing complete
                }
            }

            // If a product is unique in an order
            if (!isMatchingProduct) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("amount", 1);
                obj.put("product", product);
                productsWithAmounts.add(obj);
            }
        }

        return productsWithAmounts;
    }


}
