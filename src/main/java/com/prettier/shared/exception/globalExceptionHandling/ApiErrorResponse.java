package com.prettier.shared.exception.globalExceptionHandling;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private FriendlyMessage friendlyMessage;
    private boolean hasError;
    private List<String> errorMessages;
    private HttpStatus httpStatus;
    private LocalDateTime timeStamp;
    private String path;

}
