package com.nextinno.underground.user;

import com.nextinno.underground.domain.AbstractDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author rsjung
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractDomain{

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append(super.toString()).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", role='").append(role).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
