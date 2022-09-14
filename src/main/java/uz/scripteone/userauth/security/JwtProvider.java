/*
package uz.scripteone.userauth.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.scripteone.userauth.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private static Long EXPIRATION_DATE = 86_400_000L;
    private static String KEY = "ThisKeySecretKeyForJWT";
    public String generateToken(String username, Set<Role> roles) {
        String token =
                Jwts
                        .builder()
                        .setSubject(username)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                        .signWith(SignatureAlgorithm.HS512, KEY)
                        .claim("roles", roles)
                        .compact();
        return token;
    }

    public String getUsernameFromToken(String token) {
        String username = Jwts
                .parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username ;
    }
}
*/
