package com.hitachirail.maas.hmiadapter.service.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class User {

    private String username;

    private String password;

    private String tenant;

    private String authority;

    public User(String username, String password, PasswordEncoder encoder) {
        this.username = username;
        this.password = password;
        this.password = encoder.encode(password);
        authority = "ROLE_USER";
    }

    public User(String username, String password, String tenant, PasswordEncoder encoder) {
            this.username = username;
            this.tenant = tenant;
            this.password = encoder.encode(password);
            authority = "ROLE_USER";

    }
}
