package me.code.springboot_neo4j.services;

import lombok.NoArgsConstructor;
import me.code.springboot_neo4j.models.nodes.OrderItem;
import me.code.springboot_neo4j.models.nodes.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class OrderItemService {

    public List<OrderItem> generateOrderItems(List<Product> products) {
        List<OrderItem> items = new ArrayList<>();

        for (var product : products) {
            AddOrUpdateOrderItems(product, items);
        }
        return items;
    }

    private void AddOrUpdateOrderItems(Product product, List<OrderItem> items) {
        for (var item : items) {
            if (isMatchingProduct(product, item)) {
                updateExistingOrderItem(item);
                return;
            }
        }
        addNewOrderItem(product, items);
    }

    private boolean isMatchingProduct(Product product, OrderItem item) {
        Product productInItem = item.getProduct();
        return product.getId().equals(productInItem.getId());
    }

    private void updateExistingOrderItem(OrderItem item) {
        item.setAmount(item.getAmount() + 1);
        item.setPrice(item.getProduct().getPrice() * item.getAmount());
    }

    private void addNewOrderItem(Product product, List<OrderItem> items) {
        var newDetail = new OrderItem(product, 1);
        newDetail.setPrice(product.getPrice());
        items.add(newDetail);
    }

    public double getTotalPrice(List<OrderItem> items) {
        double sum = items.stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();

        return Math.round(sum * 100.0) / 100.0;
    }

}
