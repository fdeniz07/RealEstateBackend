package com.prettier.security.exception;

import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.auths.login.LoginFailedException;
import com.prettier.shared.utils.enums.Language;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Date;

@Component("delegatedAuthEntryPoint")
@Slf4j
public class DelegatedAuthEntryPoint implements AuthenticationEntryPoint {

    //!!!Bu sinif, yetkilendirme hatasi durumunda islem yapilmasini sagliyor

    private static final Logger logger = LoggerFactory.getLogger(DelegatedAuthEntryPoint.class);
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final long timestamp = new Date().getTime();

    public DelegatedAuthEntryPoint(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException
    ) throws IOException, ServletException {

        // 1. Önce header'dan dene
        Language language = getLanguageFromHeader(request);

        // 2. Header yoksa path'den dene
        if (language == null) {
            language = getLanguageFromPath(request.getServletPath());
        }

//        // Url deki {language} kısmını aliyoruz
//        String servletPath = request.getServletPath();
//        Language language = (Language.valueOf(servletPath.replace("/api/v1.0/auth/", "").replace("/login", "")));
//
//        log.debug("[{}][login] -> request: {}", this.getClass().getSimpleName(), request);
//        LoginFailedException loginFailedException = new LoginFailedException(language, FriendlyMessageCodes.LOGIN_FAILED_EXCEPTION, "Login failed! : Email or password is invalid.");
//
//        handlerExceptionResolver.resolveException(
//                request, response, null, loginFailedException
//        );

        // 3. Hala bulunamadıysa varsayılan
        if (language == null) {
            language = Language.EN;
        }

        log.debug("[{}][authentication failed] -> path: {}, language: {}",
                this.getClass().getSimpleName(), request.getServletPath(), language);

        LoginFailedException loginFailedException = new LoginFailedException(
                language,
                FriendlyMessageCodes.LOGIN_FAILED_EXCEPTION,
                "Authentication failed! : Access denied."
        );

        handlerExceptionResolver.resolveException(request, response, null, loginFailedException);
    }

    private Language getLanguageFromHeader(HttpServletRequest request) {
        String acceptLanguage = request.getHeader("Accept-Language");
        if (acceptLanguage != null && acceptLanguage.startsWith("tr")) {
            return Language.TR;
        } else if (acceptLanguage != null && acceptLanguage.startsWith("en")) {
            return Language.EN;
        }
        return null;
    }

    private Language getLanguageFromPath(String servletPath) {
        if (servletPath == null) {
            return null;
        }

        try {
            if (servletPath.matches("/api/v1\\.0/auth/(EN|TR)/login")) {
                String langCode = servletPath.replace("/api/v1.0/auth/", "").replace("/login", "");
                return Language.valueOf(langCode.toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            log.warn("Could not parse language from path: {}", servletPath);
        }
        return null;
    }
}


