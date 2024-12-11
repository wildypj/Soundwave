package com.wildy.Soundwave.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${application.jwtSecret}")
    private String jwtSecret; // Change to a secure key

    // Get JWt token from header
    public String getJwtFromHeader(HttpServletRequest request) {
        //extract authHeader form from request token
        String bearerToken = request.getHeader("Authorization");
        logger.info("Authorization Header: {}", bearerToken);

        // This returns just the token
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // remove Bearer prefix
        }
        return null;
    }

    //Get Username(email) from Token
    // subject is user email
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) signInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    // generate token from Email (Username)
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(signInKey())
                .compact();
    }

    // Validate JWT Token
    public boolean validateToken(String authToken){
        try{
            System.out.println("Validate");
            Jwts.parser()
                    .verifyWith((SecretKey) signInKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch(MalformedJwtException e){
            logger.error("MalformedJwtException : {}", e.getMessage());
        } catch(ExpiredJwtException e){
            logger.error("ExpiredJwtException : {}", e.getMessage());
        } catch(UnsupportedJwtException e){
            logger.error("UnsupportedJwtException : {}", e.getMessage());
        }catch(IllegalArgumentException e){
            logger.error("IllegalArgumentException : {}", e.getMessage());
        }

        return false;
    }


    // sign in key for token
    private Key signInKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}

//    public String extractUsername(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }


//public boolean validateToken(String token, UserDetails userDetails) {
//    String username = extractUsername(token);
//    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//}

//public boolean isTokenExpired(String token) {
//    return extractExpiration(token).before(new Date());
//}

//    public Date extractExpiration(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//    }

//    public boolean validateToken(String token, UserDetails userDetails) {
//        String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }