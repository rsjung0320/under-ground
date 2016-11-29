package com.nextinno.underground.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author rsjung
 *
 */
@Component
public class Common {
    private static final Logger logger = LoggerFactory.getLogger(Common.class);

    @Value("${jwt.secret}")
    private String SECRET;

    public Common() {
        logger.info("Common Start.!");
    }

    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 토큰으로 부터 username을 가져온다.
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 토큰을 생성하는 function
     * 
     * @param email
     * @param role
     * @return
     */
    public String generateToken(String email, String role) {
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 2));
//        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 1));
        return Jwts.builder().claim("roles", role).setIssuedAt(today).setExpiration(tomorrow).setIssuer(email)
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    /**
     * remember me를 선택한 사람만 주는 토큰(mobile 같은 경우도 아래와 같이 한다.)
     * 
     * @param email
     * @param role
     * @return
     */
    public String generateRefreshToken(String email, String role) {
        Date today = new Date();
        // expires 가 25일 이상만 되도 저장이 안된다. 크롬 정책인듯 하다.
        Date oneMonth = new Date(today.getTime() + (1000 * 60 * 60 * 24 * 20));

        return Jwts.builder().claim("roles", role).setIssuedAt(today).setExpiration(oneMonth).setIssuer(email)
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }
}
