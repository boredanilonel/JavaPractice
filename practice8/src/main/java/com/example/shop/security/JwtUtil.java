package com.example.shop.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "239960a3962f163e66884b4ab1c26c6d8b824b019571dc60b618ad712b1eb858015fb549ed50914c52a31b1a66aa8388de7d088fedb149d8029e03aaf711c25dd1d42d50d0dabe9763967ea562a9163c80d716fcac0a3430d6372eff760342d4ebfacece7a93215a6d112932a1c7d03c56ecdfb9b8b529985a8367ca8eb6b36df7b558ae8ff9b1828ecbbd9ffcbc5731a6843d18c8503fabc1b2e162fca1e28e5cd619f1feb601770b2a222292b33f76063146f925024cf0653b764d856b4fae12d1aa4f310dd34ab8bc0b2cd333e084f4ece3fbec562f6b35555a1eb2e09d068a0604f71f037ba6956a60cda29d542d103106bb31b704ac6d0d37e08d22af67";

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
