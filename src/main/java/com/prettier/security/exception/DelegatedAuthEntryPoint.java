package com.prettier.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("delegatedAuthEntryPoint")
public class DelegatedAuthEntryPoint implements AuthenticationEntryPoint {

    //!!!Bu sinif, yetkilendirme hatasi durumunda islem yapilmasini sagliyor

    private static final Logger logger = LoggerFactory.getLogger(DelegatedAuthEntryPoint.class);

    private final HandlerExceptionResolver handlerExceptionResolver;

    private final long timestamp = new Date().getTime();

    public DelegatedAuthEntryPoint(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException
    ) throws IOException, ServletException {

        //Logger kullanilarak yetkilendirme hatasi kaydediliyor
        logger.error("Unauthorized error : {}", authException.getMessage());

        handlerExceptionResolver.resolveException(
                request, response, null, authException
        );

//        String errorMessage = "Authentication failed: " + authException.getMessage();
//
//        //response icerigi JSON olacak ve HTTP Status Code'da 401, UnAuthorized olacacagini olusturuyorum
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        final Map<String, Object> body = new HashMap<>();
//        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//        body.put("error", "Unauthorized");
//        body.put("timestamp", timestamp);
//        body.put("message", authException.getMessage());
//        body.put("path", request.getServletPath());
//
//        //response.getWriter().write(new ObjectMapper().writeValueAsString(errorMessage));
//
//        final ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(response.getOutputStream(), body);
    }
}


