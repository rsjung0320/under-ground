package com.nextinno.underground.user;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by rsjung on 2016-11-25.
 */
public class UserDto {

    @Data
    public static class CreateUser{
        @NotBlank
        private String email;
        @NotBlank
        private String name;
        @NotBlank
        private String password;
        private String role;
    }

    @Data
    public static class ResponseUser{
        private Long idx;
        private String email;
        private String name;
        private String role;
    }
}
