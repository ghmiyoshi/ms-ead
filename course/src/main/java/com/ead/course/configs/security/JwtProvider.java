package com.ead.course.configs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class JwtProvider {
  
  @Value("${ead.auth.jwtSecret}")
  private String jtwSecret;


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

  public String getClaimNameJwt(final String authToken, final String claimName) {
    return JWT.require(Algorithm.HMAC256(jtwSecret))
        .build()
        .verify(authToken)
        .getClaim(claimName)
        .asString();
  }
}
