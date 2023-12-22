package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dtos.responses.entities.UnavailableProductDTO;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.models.nodes.ProductDetails;
import me.code.springboot_neo4j.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDetailsService {

    private ProductService productService;
    private ProductRepository productRepository;

    @Autowired
    public ProductDetailsService(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

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

    public List<UnavailableProductDTO> findUnavailableProducts(List<ProductDetails> productDetails) {
        List<UnavailableProductDTO> unavailableProductDTOS = new ArrayList<>();

        productDetails.forEach(detail -> {
            Product targetProduct = detail.getProduct();
            int requestedAmount = detail.getAmount();
            int availableAmount = productService.loadProductById(targetProduct.getId()).getQuantity();

            if ((availableAmount - requestedAmount) < 0) {
                unavailableProductDTOS.add(
                        new UnavailableProductDTO(
                                "Requested amount not available",
                                targetProduct.getId(),
                                requestedAmount,
                                availableAmount));
            }
        });

        return unavailableProductDTOS;
    }

    public void updateProductQuantities(List<ProductDetails> productDetails) {
        productDetails.forEach(details -> {
            var targetProduct = details.getProduct();
            targetProduct.setQuantity(targetProduct.getQuantity() - details.getAmount());

            productRepository.save(targetProduct);
        });
    }

}
