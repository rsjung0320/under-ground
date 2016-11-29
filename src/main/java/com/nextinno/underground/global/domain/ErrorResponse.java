package com.nextinno.underground.global.domain;

import lombok.Data;

import java.util.List;

/**
 * Created by rsjung on 2016-11-29.
 */
@Data
public class ErrorResponse {
    private String message;

    private String code;

    private List<FieldError> errors;

    public static class FieldError {
        private String message;
        private String reasonCode;
    }
}
