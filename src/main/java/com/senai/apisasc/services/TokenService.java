package com.senai.apisasc.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.senai.apisasc.models.FuncionarioModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(FuncionarioModel funcionario){
        try{
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("apisasc")
                    .withSubject(funcionario.getEmail())
                    .withClaim("id", funcionario.getId().toString())
                    .withExpiresAt(gerarValidadeToken())
                    .sign(algoritmo);

            // Log the generated token
            System.out.println("Generated token: " + token);

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro", exception);
        }
    }

    public String validarToken(String token){
        try {
            if (token == null || token.isEmpty()) {
                throw new IllegalArgumentException("Token is null or empty");
            }

            Algorithm algoritmo = Algorithm.HMAC256(secret);
            DecodedJWT jwt = JWT.require(algoritmo)
                    .withIssuer("apisasc")
                    .build()
                    .verify(token);

            return jwt.getSubject();
        } catch (JWTVerificationException | IllegalArgumentException exception){
            // Log the exception for debugging purposes
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }


    private Instant gerarValidadeToken() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
