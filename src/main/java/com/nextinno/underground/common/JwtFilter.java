package com.nextinno.underground.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {
    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Value("${jwt.secret}")
    private String SECRET;

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;

        if (!isExcludeUrl(request)) {
            logger.info("=====================doFilter====================");
            final String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                final String token = authHeader.substring(7); // The part after "Bearer "

                try {
                    final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
                    request.setAttribute("claims", claims);
                } catch (final SignatureException e) {
                    throw new ServletException("Invalid token.");
                }
            } else {
                throw new ServletException("Missing or invalid Authorization header.");
            }
        }

        chain.doFilter(req, res);
    }

    private boolean isExcludeUrl(HttpServletRequest request) {
        String uri = request.getRequestURI().toString().trim();
        if (uri.startsWith("/login/") || uri.startsWith("/api/v1/main")
                || uri.startsWith("/api/v1/artist/all/")
                || uri.startsWith("/api/v1/movie/all/")
                || uri.startsWith("/favicon.ico") 
                || uri.startsWith("/") ) {
            return true;
        } else {
            return false;
        }
    }
}

