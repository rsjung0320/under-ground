package com.nextinno.underground.login;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by rsjung on 2016-11-25.
 */
public class LoginDto {
    @Data
    public static class SignIn{
        @NotBlank
        private String email;
        @NotBlank
        private String password;
        private boolean remember;
    }
}
