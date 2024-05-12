package com.food.Sec_web.service;

import com.food.Sec_web.Repository.UserRepository;
import com.food.Sec_web.jwt.Token;
import com.food.Sec_web.jwt.TokenGenerator;
import com.food.Sec_web.model.Login;
import com.food.Sec_web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenGenerator tokenGenerator;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public Token register(User user){
//        user.setPassword(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String jwt_token=tokenGenerator.generateToken(user);
        return Token.builder().accessToken(jwt_token).build();
    }


    @Autowired
    private AuthenticationManager authenticationManager;

    public Token login(Login login){
//        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                login.getEmail(),login.getPassword()
//        ));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                login.getEmail(),login.getPassword()
        ));
        var user=userRepository.findByEmail(login.getEmail()).orElseThrow();
        String jwt_token=tokenGenerator.generateToken(user);
        return Token.builder().accessToken(jwt_token).build();
    }

}
