package com.prettier.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    //!!!Bu sinif, yetkilendirme hatasi durumunda islem yapilmasini sagliyor

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    private final long timestamp = new Date().getTime();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        //Logger kullanilarak yetkilendirme hatasi kaydediliyor
        logger.error("Unauthorized error : {}", authException.getMessage());

        String errorMessage = "Authentication failed: " + authException.getMessage();

        //response icerigi JSON olacak ve HTTP Status Code'da 401, UnAuthorized olacacagini olusturuyorum
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("timestamp", timestamp);
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        //response.getWriter().write(new ObjectMapper().writeValueAsString(errorMessage));

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}