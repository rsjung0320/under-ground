package com.nextinno.underground.token;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by rsjung on 2016-11-25.
 */
public class TokenDto {
    @Data
    public static class RefreshToken{
        @NotBlank
        private String refreshToken = "";
        @NotBlank
        private String email;
        @NotBlank
        private String role;
    }

    @Data
    public static class RegenerateToken{
        @NotBlank
        private String token = "";
        @NotBlank
        private String email;
        @NotBlank
        private String role;
        private boolean remember = false;
    }
}
