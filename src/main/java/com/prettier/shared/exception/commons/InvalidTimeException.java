package com.prettier.shared.exception.commons;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.REQUEST_TIMEOUT)
public class InvalidTimeException extends  RuntimeException{

    public InvalidTimeException(String message) {

        super(message);
    }
}
