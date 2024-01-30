package com.prettier.security.jwt;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class InternalApiAuthResponse<T> {

    private T payload;
    private boolean hasError;
    private List<String> errorMessages;
    private HttpStatus httpStatus;
    private long timestamp;
    private Map<String, String> validationErrors = new HashMap<>();

    {
        timestamp = new Date().getTime();
    }

    private String path;
}
