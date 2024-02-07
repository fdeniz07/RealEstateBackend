package com.prettier.shared.exception.globalExceptionHandling;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private FriendlyMessage friendlyMessage;
    private boolean hasError;
    private String errorMessages;
    private HttpStatus httpStatus;
    private LocalDateTime timeStamp= LocalDateTime.now();
    private String path;
    private Map<String,String> validationErrors= null;
}
