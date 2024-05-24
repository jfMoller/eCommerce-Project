package me.code.springboot_neo4j.config.neo4j;

import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockProductsConfig {

    private final ProductRepository productRepository;

    private static final List<Product> MOCK_PRODUCTS = List.of(
            new Product(
                    "Whole Bean Espresso",
                    "Premium whole bean espresso coffee for a rich and bold flavor.",
                    "https://images.heb.com/is/image/HEBGrocery/000124972-1",
                    15.99,
                    12),
            new Product(
                    "French Roast Ground Coffee",
                    "Dark and smoky French roast ground coffee for an intense brew.",
                    "https://m.media-amazon.com/images/I/71EdOynjDgL.jpg",
                    12.99,
                    12),
            new Product(
                    "Vanilla Flavored Coffee Beans",
                    "Arabica coffee beans infused with natural vanilla flavor.",
                    "https://www.webstaurantstore.com/images/products/large/735714/2504955.jpg",
                    17.99,
                    12),
            new Product(
                    "Decaf Colombian Coffee",
                    "Medium roast decaffeinated Colombian coffee beans.",
                    "https://www.freshroastedcoffee.com/cdn/shop/products/medium_134d8ab1-48f2-42ff-b935-8fe434ab6352_700x700.jpg?v=1703802767",
                    14.99,
                    12),
            new Product(
                    "Hazelnut Flavored Ground Coffee",
                    "Ground coffee with the delightful taste of roasted hazelnuts.",
                    "https://store.coffeebean.com/cdn/shop/products/hazelnut-coffee-12oz-ground.jpg?v=1619618263",
                    13.99,
                    12),
            new Product(
                    "Organic Ethiopian Yirgacheffe",
                    "Organic Ethiopian Yirgacheffe coffee beans with a fruity and floral profile.",
                    "https://images.squarespace-cdn.com/content/v1/599dab46f14aa1467634f7f2/1585340286590-BFPIMGOF87BCJP13RAQ8/12oz+Ethiopia.jpg",
                    19.99,
                    12),
            new Product(
                    "Caramel Macchiato Flavored Coffee",
                    "Creamy caramel macchiato flavored ground coffee for a sweet treat.",
                    "https://m.media-amazon.com/images/I/71ynqEV6aJL.jpg",
                    16.99,
                    12),
            new Product(
                    "Mixed Chocolate Mocha Beans",
                    "Mixed chocolate-covered mocha beans for an indulgent snack.",
                    "https://www.kroger.com/product/images/large/front/0003704123002",
                    8.99,
                    12),
            new Product(
                    "Pumpkin Spice Flavored Coffee",
                    "Seasonal pumpkin spice flavored ground coffee for autumn vibes.",
                    "https://www.newenglandcoffee.com/wp-content/uploads/2014/10/Pumpkin-Spice-Retail-Bag.jpg",
                    14.99,
                    12),
            new Product(
                    "Iced Coffee Concentrate",
                    "Concentrated iced coffee for a refreshing summer beverage.",
                    "https://m.media-amazon.com/images/I/61HM3yBbbFL.jpg",
                    9.99,
                    12));

    @Autowired
    public MockProductsConfig(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createDefaultProducts() {

        if (isProductRepositoryEmpty()) {
            MOCK_PRODUCTS.forEach(this::createProduct);
        }
    }

    public boolean isProductRepositoryEmpty() {
        return productRepository.count() == 0;
    }

    private void createProduct(Product product) {
        productRepository.save(product);
        System.out.println("MockProductsConfig created a new product: " + product.getName());
    }
}
