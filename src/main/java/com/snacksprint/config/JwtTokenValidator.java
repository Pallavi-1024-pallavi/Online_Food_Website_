package com.snacksprint.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7); // Extract the token

            try {
                // Decode the JWT using the secret key
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                // Extract user info from the token claims
                String email = claims.getSubject();  // usually email or username
                String authoritiesString = String.valueOf(claims.get("authorities"));

                // Convert comma-separated authorities into a list of GrantedAuthority objects
                List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesString);

                // Create the authentication token
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Handle JWT parsing exceptions
                throw new BadCredentialsException("Invalid or expired JWT token", e);
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
