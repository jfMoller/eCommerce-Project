package me.code.springboot_neo4j.utils;

import lombok.Getter;
import me.code.springboot_neo4j.models.nodes.ProductDetail;
import me.code.springboot_neo4j.models.nodes.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductDetailUtil {

    public static List<ProductDetail> parseAsProductDetails(List<Product> orderedProducts) {
        System.out.println(orderedProducts);
        List<ProductDetail> groupedProducts = new ArrayList<>();
        System.out.println(groupedProducts);
        for (Product orderedProduct : orderedProducts) {
            boolean isMatchingProduct = false;

            for (ProductDetail groupedProduct : groupedProducts) {
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
                var newOrderDetails = new ProductDetail(orderedProduct, 1);
                newOrderDetails.setGroupPrice(orderedProduct.getPrice());
                groupedProducts.add(newOrderDetails);
            }
        }
        return groupedProducts;
    }

    public static double getTotalPrice(List<ProductDetail> details) {
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
