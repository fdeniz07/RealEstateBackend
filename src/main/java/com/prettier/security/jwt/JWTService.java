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

@Service
public class JWTService{

    @Value("${backendapi.app.jwtSecret}")
    //application.properties'den degeri al diyoruz. Bu sekilde sabit degerlerimizi kodlar arasinda degil,ayar dosyasinda degistirebiliyoruz
    private String jwtSecret;

    @Value("${backendapi.app.jwtExpressionMS}")
    private Long jwtExpirationMs;

    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);

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
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //NOT: Token icin secret key olusturuyoruz, degeri application.yaml dan okuyoruz
    private Key getSignInKey() {
        byte[] key = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(key);
    }


    //Not: JWT Token üzerinden username alma ***************************************
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }


    //Not: JWT Token üzerinden claim bilgisini alma ********************************
    private Claims extractAllClaims(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Not: Validate JWT **********************************************************
    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = extractUsername(token);

        try {
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            logger.error("Jwt token is expired : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Jwt token is unsupported : {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid Jwt token : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Jwt claims string is empty : {}", e.getMessage());
        }
        return false;
    }

    //Not: Token Süre Kontrolü ******************************************************
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

}
