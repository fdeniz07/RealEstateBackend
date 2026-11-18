package com.prettier.security.exception;

import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.adverts.AdvertCreateForbiddenException;
import com.prettier.shared.utils.enums.Language;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component("delegatedAccessDeniedHandler")
@Slf4j
public class DelegatedAccessDeniedHandler implements AccessDeniedHandler {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public DelegatedAccessDeniedHandler(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        Language language = resolveLanguage(request);
        log.debug(
                "[{}][access denied] -> path: {}, language: {}",
                this.getClass().getSimpleName(),
                request.getServletPath(),
                language);

        AdvertCreateForbiddenException forbidden =
                new AdvertCreateForbiddenException(
                        language,
                        FriendlyMessageCodes.ADVERT_CREATE_FORBIDDEN,
                        accessDeniedException.getMessage());

        handlerExceptionResolver.resolveException(request, response, null, forbidden);
    }

    private Language resolveLanguage(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        if (servletPath != null) {
            try {
                String[] segments = servletPath.split("/");
                for (String segment : segments) {
                    if ("EN".equalsIgnoreCase(segment)) {
                        return Language.EN;
                    }
                    if ("TR".equalsIgnoreCase(segment)) {
                        return Language.TR;
                    }
                }
            } catch (IllegalArgumentException e) {
                log.warn("Could not parse language from path: {}", servletPath);
            }
        }
        return Language.EN;
    }
}