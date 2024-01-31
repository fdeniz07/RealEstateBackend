package com.prettier.shared.exception.globalExceptionHandling;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiErrorResponse {
    private FriendlyMessage friendlyMessage;
    private boolean hasError;
    private List<String> errorMessages;
    private HttpStatus httpStatus;
    private LocalDateTime timeStamp;
    private String path;

}
