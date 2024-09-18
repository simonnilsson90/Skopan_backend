package in.mahesh.tasks.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);
        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000)) // 24h expiration
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
        System.out.println("Generated JWT: " + jwt); // Kontrollera att detta är en korrekt formaterad JWT
        return jwt;
    }

    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for(GrantedAuthority authority: authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }


    @SuppressWarnings("deprecation")
    public String getEmailFromJwtToken(String token) {
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JwtConstant.SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // Assuming the email is stored as the subject
        } catch (Exception e) {
            System.out.println("Error extracting email from JWT: " + e.getMessage());
            return null;
        }
    }


}

