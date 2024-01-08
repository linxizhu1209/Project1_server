package com.github.backend_1st_project.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final String secretKey = Base64.getEncoder()
            .encodeToString("project1".getBytes());

    private long tokenValidMillisecond = 1000L*60*60;

    private final UserDetailsService userDetailsService;

    // request Header에 따로 필드를 만들 수도 있지만, 기본적으로 request Header에서 Authorization이 존재
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    public String createToken(String email, List<String> roles){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles",roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    public boolean validateToken(String jwtToken){
        try{
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
            Date now = new Date();
            return claims.getExpiration().after(now);
        } catch (Exception e){
            return false;
        }
    }

    public boolean nullifyToken(String jwtToken){
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody().setSubject("nullToken");
        return true;
    }

    public Authentication getAuthentication(String jwtToken){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserEmail(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    private String getUserEmail(String jwtToken){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody().getSubject();
    }
}
