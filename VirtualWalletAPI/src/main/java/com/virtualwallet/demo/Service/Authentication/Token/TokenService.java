package com.virtualwallet.demo.Service.Authentication.Token;

import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.virtualwallet.demo.Model.User.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService implements IToken
{
    @Value("${api.security.token.secret}")
    private String secret;

    @Override
    public String generateToken(User user)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer("wallet-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(tokenExpireHour())
                    .sign(algorithm);
        }catch (JWTCreationException e)
        {
            throw new RuntimeException("Error to create token", e);
        }
    }

    public Instant tokenExpireHour()
    {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

    @Override
    public String validateToken(String token)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("wallet-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException e)
        {
            throw new RuntimeException("Error to validate token", e);
        }
    }

}
