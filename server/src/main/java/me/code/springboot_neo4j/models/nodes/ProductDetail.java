package me.code.springboot_neo4j.models.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("ProductDetail")
public class ProductDetail {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    String id;

    @Relationship(value = "REFERS_TO", direction = Relationship.Direction.OUTGOING)
    private Product product;

    private int amount;
    private double groupPrice;

    public ProductDetail(Product product, int amount) {
        this.product = product;
        this.amount = amount;
        this.groupPrice = product.getPrice() * amount;
    }

    public static List<ProductDetail> generateProductDetails(List<Product> orderedProducts) {
        System.out.println(orderedProducts);
        List<ProductDetail> productDetails = new ArrayList<>();
        System.out.println(productDetails);

        for (var orderedProduct : orderedProducts) {
            boolean isMatchingProduct = false;

            for (var productDetail : productDetails) {
                var product = productDetail.getProduct();

                if (product.getId().equals(orderedProduct.getId())) {
                    // If the product is already in the group, update the group
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

    private static void addNewProductDetail(Product orderedProduct, List<ProductDetail> productDetails) {
        var newDetail = new ProductDetail(orderedProduct, 1);
        newDetail.setGroupPrice(orderedProduct.getPrice());

        productDetails.add(newDetail);
    }


    @Override
    public String toString() {
        return "ProductDetail{" +
                "id='" + id + '\'' +
                ", product=" + product +
                ", amount=" + amount +
                ", groupPrice=" + groupPrice +
                '}';
    }
}