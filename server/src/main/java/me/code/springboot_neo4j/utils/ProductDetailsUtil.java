package me.code.springboot_neo4j.utils;

import lombok.Getter;
import me.code.springboot_neo4j.models.ProductDetails;
import me.code.springboot_neo4j.models.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductDetailsUtil {

    public static List<ProductDetails> parseAsProductDetails(List<Product> orderedProducts) {
        System.out.println(orderedProducts);
        List<ProductDetails> groupedProducts = new ArrayList<>();
        System.out.println(groupedProducts);
        for (Product orderedProduct : orderedProducts) {
            boolean isMatchingProduct = false;

            for (ProductDetails groupedProduct : groupedProducts) {
                var productInGroup = groupedProduct.getProduct();

                if (productInGroup.getId().equals(orderedProduct.getId())) {
                    // If the product is already in the group, update the group
                    groupedProduct.setAmount(groupedProduct.getAmount() + 1);
                    groupedProduct.setGroupPrice(productInGroup.getPrice() * groupedProduct.getAmount());

                    isMatchingProduct = true;
                    break;
                }
            }

            if (!isMatchingProduct) {
                // If the product is not in any existing group, create a new group
                var newOrderDetails = new ProductDetails(orderedProduct, 1);
                newOrderDetails.setGroupPrice(orderedProduct.getPrice());
                groupedProducts.add(newOrderDetails);
            }
        }
        return groupedProducts;
    }

    public static double getTotalPrice(List<ProductDetails> details) {
        double sum = 0;

        for (var detail : details) {
            sum += detail.getGroupPrice();
        }

        return formatPrice(sum);
    }

    private static double formatPrice(double price) {
        return Math.round(price * 100.0) / 100.0;
    }
}
