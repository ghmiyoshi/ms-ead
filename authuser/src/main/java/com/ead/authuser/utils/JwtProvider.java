package com.ead.authuser.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ead.authuser.models.UserDetailsImpl;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class JwtProvider {

  private static final String ZONE_ID = "America/Sao_Paulo";

  @Value("${ead.auth.jwtSecret}")
  private String jtwSecret;

  @Value("${ead.auth.jwtExpiration}")
  private int jwtExpirationMs;

  public String generateToken(final UserDetailsImpl userDetails) {
    log.info("[method:generateToken] Generate token");
    try {
      return JWT.create()
          .withSubject(userDetails.getUsername())
          .withIssuedAt(Instant.now())
          .withExpiresAt(expirationDate())
          .sign(Algorithm.HMAC256(jtwSecret));
    } catch (JWTCreationException exception) {
      log.error("[method:generateToken] Error generate token jwt: {}", exception.getMessage());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error generate token jwt",
          exception);
    }
  }

  private Instant expirationDate() {
    return LocalDateTime.now().atZone(ZoneId.of(ZONE_ID)).toInstant().plusMillis(jwtExpirationMs);
  }

  public String getUserNameFromJwtToken(final String token) {
    log.info("[method:getUserNameFromJwtToken] Get username from token");
    return JWT.require(Algorithm.HMAC256(jtwSecret))
        .build()
        .verify(token)
        .getSubject();
  }

  public boolean validateJwtToken(final String authToken) {
    log.info("[method:validateJwtToken] Validate token");
    try {
      JWT.require(Algorithm.HMAC256(jtwSecret))
          .build()
          .verify(authToken);
      return true;
    } catch (Exception e) {
      log.error("[method:validateJwtToken] Invalid token: {}", e.getMessage());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");
    }
  }
}
