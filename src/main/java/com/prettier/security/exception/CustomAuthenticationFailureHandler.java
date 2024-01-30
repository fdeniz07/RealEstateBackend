package com.prettier.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prettier.shared.exception.exceptions.users.ConflictException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final long timestamp = new Date().getTime();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> responseBody = getStringObjectMap(request, exception);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
    private Map<String, Object> getStringObjectMap(HttpServletRequest request, AuthenticationException exception) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.UNAUTHORIZED.value());
        responseBody.put("error", "Unauthorized");
        responseBody.put("timestamp", timestamp);

        if (exception instanceof BadCredentialsException) {
            // Eğer kullanıcı email veya şifre geçerli değilse
            responseBody.put("message", "Invalid email or password");
        } else if (exception instanceof DuplicateUserException) {
            responseBody.put("message", "This user already exist");
        } else {
            // Diğer türde hatalar için genel bir mesaj
            responseBody.put("message", "Authentication failed");
        }
        responseBody.put("path", request.getServletPath());
        return responseBody;
    }
}
