package com.hitachirail.maas.hmiadapter.config;

import com.hitachirail.maas.hmiadapter.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class HmiSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private AppUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Set up simplified security settings requiring all requests to be authenticated
        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "/index", "/webjars/**", "/hmi-adapter-websocket/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // Create an AuthenticationManager bean to Authenticate users in the ChannelInterceptor
    @Bean
    public AuthenticationManager authManager() throws Exception {
        return this.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}
