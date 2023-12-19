package me.code.springboot_neo4j.services;

import lombok.NoArgsConstructor;
import me.code.springboot_neo4j.dto.response.entity.UnavailableProduct;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.models.nodes.ProductDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class ProductDetailsService {

    public List<ProductDetails> generateProductDetails(List<Product> orderedProducts) {
        List<ProductDetails> productDetails = new ArrayList<>();

        for (var orderedProduct : orderedProducts) {
            boolean isMatchingProduct = false;

            for (var productDetail : productDetails) {
                var product = productDetail.getProduct();

                if (product.getId().equals(orderedProduct.getId())) {
                    productDetail.setAmount(productDetail.getAmount() + 1);
                    productDetail.setGroupPrice(product.getPrice() * productDetail.getAmount());
                    isMatchingProduct = true;
                    break;
                }
            }

            if (!isMatchingProduct) {
                addNewProductDetail(orderedProduct, productDetails);
            }
        }
        return productDetails;
    }

    private void addNewProductDetail(Product orderedProduct, List<ProductDetails> productDetails) {
        var newDetail = new ProductDetails(orderedProduct, 1);
        newDetail.setGroupPrice(orderedProduct.getPrice());
        productDetails.add(newDetail);
    }

    public double getTotalPrice(List<ProductDetails> productDetails) {
        double sum = productDetails.stream()
                .mapToDouble(ProductDetails::getGroupPrice)
                .sum();

        return Math.round(sum * 100.0) / 100.0;
    }

    public List<UnavailableProduct> findUnavailableProducts(List<ProductDetails> productDetails) {
        List<UnavailableProduct> unavailableProducts = new ArrayList<>();

        productDetails.forEach(detail -> {
            Product targetProduct = detail.getProduct();
            int requestedAmount = detail.getAmount();
            int availableAmount = targetProduct.getQuantity();

            if ((availableAmount - requestedAmount) < 0) {
                unavailableProducts.add(
                        new UnavailableProduct(
                                "Requested amount not available",
                                targetProduct.getId(),
                                requestedAmount,
                                availableAmount));
            }
        });

        return unavailableProducts;
    }

}
