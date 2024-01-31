package com.prettier.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.time.Instant;

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
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername()) //UserDetails deki unique alani giriyoruz (username/email/...)
                .setIssuedAt(new Date(System.currentTimeMillis())) //Token olusturulma tarihi
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs)) // Token bitis süresi 24 saat + 1000ms olaray ayarliyoruz (1000+ 1000x60x60x24). Bu veri application.yaml den cekiliyor
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    //Not: JWT Token üzerinden alinan claim bilgisinin cözümlenmesi *****************
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    //NOT: Token icin secret key olusturuyoruz, degeri application.yaml dan okuyoruz
    private Key getSignInKey() {
        byte[] key = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(key);
    }


    //Not: JWT Token üzerinden username alma ***************************************
    public String getSubject(String token) {

        return getClaims(token).getSubject();
    }


    //Not: JWT Token üzerinden claim bilgisini alma ********************************
    private Claims getClaims(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
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

//    private boolean isTokenExpired(String token) {
//
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//
//        return extractClaim(token, Claims::getExpiration);
//    }

}
