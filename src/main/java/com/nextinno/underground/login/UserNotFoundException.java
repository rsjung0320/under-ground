package com.nextinno.underground.login;

/**
 * Created by rsjung on 2016-11-25.
 */
public class UserNotFoundException extends RuntimeException {
    private String email;

    public UserNotFoundException(String email) {
        this.email = email;
    }

    public String getEmail() { return email; }
}
