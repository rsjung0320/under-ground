package com.nextinno.underground.user;

/**
 * Created by rsjung on 2016-11-25.
 */
public class AlreadyExistUserException extends RuntimeException {

    private String email;

    public AlreadyExistUserException(String email) {
        this.email = email;
    }

    public String getEmail() { return email; }
}
