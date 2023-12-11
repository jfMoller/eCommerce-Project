package me.code.springboot_neo4j.utils;

import lombok.Getter;
import me.code.springboot_neo4j.models.GroupedProduct;
import me.code.springboot_neo4j.models.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductGroupingUtil {

    public static List<GroupedProduct> parseAsProductGroups(List<Product> orderedProducts) {
        System.out.println(orderedProducts);
        List<GroupedProduct> groupedProducts = new ArrayList<>();
        System.out.println(groupedProducts);
        for (Product orderedProduct : orderedProducts) {
            boolean isMatchingProduct = false;

            for (GroupedProduct groupedProduct : groupedProducts) {
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
                var newGroupedProduct = new GroupedProduct(orderedProduct, 1);
                newGroupedProduct.setGroupPrice(orderedProduct.getPrice());
                groupedProducts.add(newGroupedProduct);
            }
        }
        return groupedProducts;
    }

    public static double getTotalPrice(List<GroupedProduct> groupedProducts) {
        double sum = 0;

        for (var group : groupedProducts) {
            sum += group.getGroupPrice();
        }

        return formatPrice(sum);
    }

    private static double formatPrice(double price) {
        return Math.round(price * 100.0) / 100.0;
    }
}
