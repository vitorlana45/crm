package com.lanDev.crm.adapter.inbound.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lanDev.crm.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class TokenService {

    @Value("${security.secretKey}")
    private String secret;

    @Value("${security.expirationToken}")
    private Long expirationToken;

    @Value("${security.issuer}")
    private String ISSUER;


    public String generateToken(User user) {

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("CRM")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

        }catch(Exception e){
            return "";
        }
    }

    public String validateToken (String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException e){
            return "";
        }
    }

    public Instant generateExpirationDate (){
        return LocalDateTime.now().plusHours(expirationToken).toInstant(ZoneOffset.of("-03:00"));
    }

    public Long getExpirationToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getExpiresAt().toInstant().getEpochSecond();
    }
}
