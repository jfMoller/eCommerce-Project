package me.code.springboot_neo4j.dto.response.success;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class Success {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("message")
    private String message;

    public Success(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.success = true;
        this.status = status;
        this.message = message;
    }

    public ResponseEntity<Success> toResponseEntity() {
        return ResponseEntity.status(this.status).body(this);
    }
}