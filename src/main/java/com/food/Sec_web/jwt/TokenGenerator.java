//package com.food.Sec_web.jwt;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//
//@Service
//public class TokenGenerator {
//
//    private final String SECRET="daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb";
//
//    public String generateToken(UserDetails user){
//        return Jwts.builder().setSubject(user.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+86400000))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    private Key getSignKey(){
//        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//
//
//
//}



package com.food.Sec_web.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Service
public class TokenGenerator {

    private final String SECRET="daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb";

    public String generateToken(UserDetails user){
        return Jwts.builder().setSubject(user.getUsername())
                .claim("authorities",givenAuthorities(user.getAuthorities()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String givenAuthorities(Collection<? extends GrantedAuthority> authorities){
        Set<String> authoritySet=new HashSet<>();
        for(GrantedAuthority auth:authorities){
            authoritySet.add(auth.getAuthority());
        }
        return String.join(",",authoritySet);
    }


    // extract all claims present in token
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSignKey())
                .build().parseClaimsJws(token).getBody();
    }

  // token => extractAll claims => resolve only subject claim (username)  [extractUserName]

   // i am extracting all the claims from the token
    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // and from those claims i want only subject that is userName
    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }






}
