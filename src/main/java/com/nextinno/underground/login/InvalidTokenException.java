package com.nextinno.underground.login;

/**
 * Created by rsjung on 2016-11-25.
 */
public class InvalidTokenException extends RuntimeException {
    private String email;

    public InvalidTokenException(String email) {
        this.email = email;
    }

    public String getEmail() { return email; }
}
