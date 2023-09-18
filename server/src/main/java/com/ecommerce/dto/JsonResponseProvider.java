package com.ecommerce.dto;

import com.ecommerce.entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static com.ecommerce.dto.ResponseStatus.SUCCESS;

public class JsonResponseProvider {

    public static ResponseEntity<Object> sendAuthenticationEntity(Role userRole, String jwtToken) {
        Map<String, Object> response = new HashMap<>();
        response.put(SUCCESS.toString(), true);
        response.put("userRole", userRole);
        response.put("token", jwtToken);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static ResponseEntity<Object> sendResponseEntity(
            ResponseStatus responseStatus, HttpStatus httpStatus, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put(responseStatus.toString(), true);
        response.put("message", message);

        return ResponseEntity.status(httpStatus).body(response);
    }
}
