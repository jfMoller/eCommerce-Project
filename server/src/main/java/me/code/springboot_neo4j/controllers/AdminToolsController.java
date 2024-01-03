package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dtos.requests.AddProductDTO;
import me.code.springboot_neo4j.dtos.requests.ChangeExpectedDeliveryDTO;
import me.code.springboot_neo4j.dtos.requests.EditedProductDTO;
import me.code.springboot_neo4j.dtos.requests.SendOrderDTO;
import me.code.springboot_neo4j.dtos.responses.entities.UserOrderDTO;
import me.code.springboot_neo4j.dtos.responses.success.Success;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.services.AdminToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin_tools")
public class AdminToolsController {
    private final AdminToolsService adminToolsService;

    @Autowired
    public AdminToolsController(AdminToolsService adminToolsService) {
        this.adminToolsService = adminToolsService;
    }

    @PostMapping("/product/add")
    public ResponseEntity<Product> addProduct(@RequestBody AddProductDTO dto) {
        var result = adminToolsService.addProduct(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/product/edit/{productId}")
    public ResponseEntity<Success> editProduct(@PathVariable String productId, @RequestBody EditedProductDTO dto) {
        var result = adminToolsService.editProduct(productId, dto);
        return result.toResponseEntity();
    }

    @DeleteMapping("/product/delete/{productId}")
    public ResponseEntity<Success> deleteProduct(@PathVariable String productId) {
        var result = adminToolsService.deleteProduct(productId);
        return result.toResponseEntity();
    }

    @GetMapping("/order/all")
    public ResponseEntity<List<UserOrderDTO>> getAllUsersOrders() {
        var result = adminToolsService.getAllUsersOrders();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/order/all/{status}")
    public ResponseEntity<List<UserOrderDTO>> getAllUsersOrders(@PathVariable String status) {
        var result = adminToolsService.getAllUsersOrders(status);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/order/send")
    public ResponseEntity<Success> sendOrder(@RequestBody SendOrderDTO dto) {
        System.out.println("Con");
        var result = adminToolsService.sendOrder(dto.orderId(), dto.expectedDelivery());
        return result.toResponseEntity();
    }

    @PatchMapping("/order/expected_delivery")
    public ResponseEntity<Success> changeExpectedDelivery(@RequestBody ChangeExpectedDeliveryDTO dto) {
        var result = adminToolsService.changeExpectedDelivery(dto.orderId(), dto.newExpectedDelivery());
        return result.toResponseEntity();
    }

}
