package com.hms.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryTime;
    long expiryDuration= (long)expiryTime;

    private Algorithm algorithm;
    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
        expiryDuration = (long) expiryTime;  // Ensure this is initialized correctly
    }
    public String generateToken(String username) {
        return JWT.create()
                .withClaim("name", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiryDuration))
                .withIssuer(issuer)
                .sign(algorithm);
    }
    public String getUseName(String token){
        DecodedJWT decodedJWT = JWT.require(algorithm).
                withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim("name").asString();


    }
}
