package com.hitachirail.maas.hmiadapter.service;

import com.hitachirail.maas.hmiadapter.service.model.AppUserDetails;
import com.hitachirail.maas.hmiadapter.service.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AppUserDetailsService implements UserDetailsService {

    @Value(value = "${http.security.base.username}")
    String username;

    @Value(value = "${http.security.base.password}")
    String password;

    @Value(value = "${http.security.base.username2}")
    String username2;

    @Value(value = "${http.security.base.password2}")
    String password2;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = null;
        PasswordEncoder encoder= new BCryptPasswordEncoder(10);
        if(s.equals(username) ){
            user = new User(s, password, encoder );
        }else if(s.equals(username2) ){
            user = new User(s, password2, encoder );
        }

        if(user == null){
            throw new UsernameNotFoundException("User does not exist");
        }
        return new AppUserDetails(user);

    }
}
