package me.code.springboot_neo4j.dto.response.success.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.models.Product;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OngoingOrderSuccess extends Success {

    @JsonProperty("products")
    private List<Map<String, Object>> products;

    @JsonProperty("totalPrice")
    private double totalPrice;

    public OngoingOrderSuccess(HttpStatus status, String message, List<Product> products, double totalPrice) {
        super(status, message);
        this.products = parseAsProductWithAmount(products);
        this.totalPrice = totalPrice;
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
