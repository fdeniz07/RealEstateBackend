package com.prettier.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${backendapi.app.jwtSecret}")
    //application.properties'den degeri al diyoruz. Bu sekilde sabit degerlerimizi kodlar arasinda degil,ayar dosyasinda degistirebiliyoruz
    private String jwtSecret;

    @Value("${backendapi.app.jwtExpressionMS}")
    private Long jwtExpirationMs;

    //Not: Generate JWT **********************************************************
    //JWT Loginden sonra cagirilir

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs)) //Burada biz anlik zamani al, daha önce belirledigimiz süreyi (1 gün) üzerine ekle ve gecerlilik süresi olarak kabul et diyoruz
//              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Burada süreyi 1 gün ayarliyoruz ama yukaridaki kod ile hazir veriyoruz
                .signWith(SignatureAlgorithm.HS512, jwtSecret)  //Hangi sifreleme algoritmasini kullanacagimizi seciyoruz
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Not: JWT Token üzerinden alinan claim bilgisinin cözümlenmesi *****************
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    //NOT: Token icin secret key olusturuyoruz, degeri application.yaml dan okuyoruz
    private Key getSignInKey() {
        byte[] key = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(key);
    }


    //Not: JWT Token üzerinden username alma ***************************************
    public String extractUserName(String token) {

        return extractClaim(token, Claims::getSubject);
    }


    //Not: JWT Token üzerinden claim bilgisini alma ********************************
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    //Not: Validate JWT **********************************************************
    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //Not: Token Süre Kontrolü ******************************************************
    private boolean isTokenExpired(String token) {

        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    //Not: getUsernameForJwt *****************************************************
    public String getUserNameFromJwtToken(String token) {

        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
