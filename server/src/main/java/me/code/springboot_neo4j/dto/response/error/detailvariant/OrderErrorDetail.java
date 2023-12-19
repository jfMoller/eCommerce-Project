package me.code.springboot_neo4j.dto.response.error.detailvariant;

import lombok.Getter;
import me.code.springboot_neo4j.dto.response.entity.UnavailableProduct;
import me.code.springboot_neo4j.dto.response.error.ErrorDetail;

import java.util.List;

@Getter
public class OrderErrorDetail extends ErrorDetail {

    private List<UnavailableProduct> unavailableProducts;

    public OrderErrorDetail(String message, List<UnavailableProduct> unavailableProducts) {
        super(message);
        this.unavailableProducts = unavailableProducts;
    }

}
