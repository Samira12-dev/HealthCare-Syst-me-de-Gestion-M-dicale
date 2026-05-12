package com.example.HealthCare.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    // inject secret key from application.properties
    @Value("${app.secret-key}")
    private String secretKey;

    // inject token expiration time
    @Value("${app.expiration-time}")
    private long expirationTime;

    // generate jwt token
    public String generateToken(String username) {

        Map<String, Object> claims = new HashMap<>();

        return createToken(claims, username);
    }

    // create jwt token
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + expirationTime)
                )
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // generate signing key
    private Key getSignKey() {

        byte[] keyBytes = secretKey.getBytes();

        return new SecretKeySpec(
                keyBytes,
                SignatureAlgorithm.HS256.getJcaName()
        );
    }

    // validate token
    public Boolean validateToken(
            String token,
            UserDetails userDetails
    ) {

        String username = extractUsername(token);

        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(token)
        );
    }

    // check if token expired
    private boolean isTokenExpired(String token) {

        return extractExpirationTime(token).before(new Date());
    }

    // extract expiration date
    private Date extractExpirationTime(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    // extract username
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    // extract specific claim
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    // extract all claims
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();
    }
}