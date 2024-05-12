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




}
