package com.prettier.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTUtils {

    @Value("${backendapi.app.jwtSecret}")
    //application.properties'den degeri al diyoruz. Bu sekilde sabit degerlerimizi kodlar arasinda degil,ayar dosyasinda degistirebiliyoruz
    private String jwtSecret;

    @Value("${backendapi.app.jwtExpressionMS}")
    private Long jwtExpirationMs;

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    //Not: Generate JWT **********************************************************
    //JWT Loginden sonra cagirilir
    public String generateToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails);
    }


    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .claims(extractClaims)
                .subject(userDetails.getUsername()) //UserDetails deki unique alani giriyoruz (username/email/...)
                .issuedAt(new Date(System.currentTimeMillis())) //Token olusturulma tarihi
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // Token bitis süresi application.yaml den cekiliyor
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }


    //NOT: Token icin secret key olusturuyoruz, degeri application.yaml dan okuyoruz
    private SecretKey getSignInKey() {
        byte[] key = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(key);
    }


    //Not: JWT Token üzerinden username alma ***************************************
    public String getSubject(String token) {

        return getClaims(token).getSubject();
    }


    //Not: JWT Token üzerinden claim bilgisini alma ********************************
    private Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Not: Validate JWT **********************************************************
    public boolean isTokenValid(String jwt, String username) {
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    //Not: Token Süre Kontrolü ******************************************************
    private boolean isTokenExpired(String jwt) {
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }

}