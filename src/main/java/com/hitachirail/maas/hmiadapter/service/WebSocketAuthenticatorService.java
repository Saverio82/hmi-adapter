package com.hitachirail.maas.hmiadapter.service;


import com.hitachirail.maas.hmiadapter.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class WebSocketAuthenticatorService {

    @Autowired
    private AuthenticationManager authManager;

    @Value(value = "${http.security.base.username}")
    String username;

    @Value(value = "${http.security.base.password}")
    String password;

    @Value(value = "${http.security.base.username2}")
    String username2;

    @Value(value = "${http.security.base.password2}")
    String password2;

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(String username, String password, String tenant) throws AuthenticationException {

        // Check the username and password are not empty
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
        }
        // Check that the user with that username exists
        User user = null;
        PasswordEncoder encoder= new BCryptPasswordEncoder(10);
        if(username.equals(this.username) && password.equals(this.password)){
            user = new User(username, password, tenant, encoder);
        }else if(username.equals(this.username2) && password.equals(this.password2)){
            user = new User(username, password, tenant, encoder);
        }
        if(user == null){
            throw new AuthenticationCredentialsNotFoundException("User not found");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username,
                password,
                Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority()))

        );

        token.setDetails(user);

        // verify that the credentials are valid
        authManager.authenticate(token);

        return token;
    }


}
