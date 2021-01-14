package com.kiprenko.springfield.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kiprenko.springfield.security.SecurityConstants.AUTHORITIES;
import static com.kiprenko.springfield.security.SecurityConstants.AUTHORITY;

@Log4j2
public class JwtTokenVerifyingFilter extends OncePerRequestFilter {

    private final JwtConfiguration jwtConfiguration;
    private final SecretKey secretKey;

    public JwtTokenVerifyingFilter(JwtConfiguration jwtConfiguration, SecretKey secretKey) {
        this.jwtConfiguration = jwtConfiguration;
        this.secretKey = secretKey;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtConfiguration.getAuthorizationHeader());
        String tokenPrefix = jwtConfiguration.getTokenPrefix();
        if (authorizationHeader == null || authorizationHeader.isBlank() || !authorizationHeader.startsWith(tokenPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.replace(tokenPrefix, "");

        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String username = body.getSubject();
            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get(AUTHORITIES);

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> m.get(AUTHORITY))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (JwtException ex) {
            LOGGER.error(String.format("Token %s cannot be trust", token), ex);
            throw new IllegalStateException();
        }
    }
}
