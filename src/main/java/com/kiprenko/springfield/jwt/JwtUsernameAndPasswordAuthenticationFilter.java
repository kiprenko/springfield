package com.kiprenko.springfield.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.kiprenko.springfield.security.SecurityConstants.AUTHORITIES;

@Log4j2
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey secretKey;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      JwtConfiguration jwtConfiguration,
                                                      SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfiguration = jwtConfiguration;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()
            );

            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim(AUTHORITIES, authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(jwtConfiguration.getTokenExpirationHours(), ChronoUnit.HOURS)))
                .signWith(secretKey)
                .compact();
        response.addHeader(jwtConfiguration.getAuthorizationHeader(), jwtConfiguration.getTokenPrefix() + token);
    }
}
