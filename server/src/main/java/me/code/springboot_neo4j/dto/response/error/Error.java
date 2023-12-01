package me.code.springboot_neo4j.dto.response.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class Error {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    @JsonProperty("error")
    private Boolean error;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("errorDetails")
    private ErrorDetail errorDetail;

    public Error(HttpStatus status, Throwable ex) {
        this.timestamp = LocalDateTime.now();
        this.error = true;
        this.status = status;
        this.message = ex.getMessage();
        this.errorDetail = null;
    }

    public <T extends ErrorDetail> void addErrorDetail(T errorDetail) {
        this.errorDetail = errorDetail;
    }

    public ResponseEntity<Error> toResponseEntity() {
        return ResponseEntity.status(this.status).body(this);
    }
}