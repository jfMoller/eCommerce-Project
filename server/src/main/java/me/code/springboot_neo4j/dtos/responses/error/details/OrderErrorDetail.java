package me.code.springboot_neo4j.dtos.responses.error.details;

import lombok.Getter;
import me.code.springboot_neo4j.dtos.responses.entities.UnavailableProductDTO;
import me.code.springboot_neo4j.dtos.responses.error.ErrorDetail;

import java.util.List;

@Getter
public class OrderErrorDetail extends ErrorDetail {

    private List<UnavailableProductDTO> unavailableProducts;

    public OrderErrorDetail(String message, List<UnavailableProductDTO> unavailableProducts) {
        super(message);
        this.unavailableProducts = unavailableProducts;
    }

}
