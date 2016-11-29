package com.nextinno.underground.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author rsjung
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private Long idx;

    @Column(name = "email", nullable = false, unique = true)
    private String email = "";

    @Column(name = "name", nullable = false)
    private String name = "";

    @Column(name = "password", nullable = false)
    private String password = "";

    // user / admin
    @Column(name = "role", nullable = false)
    private String role = "USER";

    public User() {}

    public User(UserDto.CreateUser user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();

        if (user.getRole() != null) {
            if(!user.getRole().equals("ADMIN")) {
                this.role = "USER";
            } else {
                this.role = "ADMIN";
            }
        }
    }
}
