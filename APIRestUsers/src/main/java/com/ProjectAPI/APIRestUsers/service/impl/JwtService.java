package com.ProjectAPI.APIRestUsers.service.impl;

import com.ProjectAPI.APIRestUsers.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    //creo una secret key 256based https://asecuritysite.com/encryption/plain da qui
    private final String SECRET_KEY = "6210f866e2c23062327fe6d1b222b06cc297740c6fbadab9145adaa8563ecbb9";

    //creo un token con exp 24h basato sullo username
    public String generateToken(User user){
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    //metodo per codificare la key
    private SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //metodo per prendere tutti i claims
    private Claims exactAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //metodo per estrarre un ospecifico claim
    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = exactAllClaims(token);
        return resolver.apply(claims);
    }

    //metodi per estrarre username e exp da un token
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    //metodi di controllo della validità
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isValid(String token, UserDetails user){
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

}
