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
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                //.signWith(SignatureAlgorithm.HS512, jwtSecret)  //Hangi sifreleme algoritmasini kullanacagimizi seciyoruz
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {

        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    //!!! Artik kullanmiyoruz, degeri application.yaml dan okuyoruz
    private Key getSiginKey() {
        byte[] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(key);
    }

    public String extractUserName(String token) {

        return extractClaim(token, Claims::getSubject);
    }


    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
    }

    //Not: Validate JWT **********************************************************
    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {

        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    //Not: getUsernameForJwt *****************************************************
    public String getUserNameFromJwtToken(String token) {

        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
