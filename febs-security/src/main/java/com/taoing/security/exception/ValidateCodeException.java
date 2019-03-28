package com.taoing.security.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -7171483384784265379L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
