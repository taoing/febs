package com.taoing.security.exception;

import org.springframework.security.core.AuthenticationException;

public class FebsCredentialException extends AuthenticationException {

    private static final long serialVersionUID = 515121284549012286L;

    public FebsCredentialException(String message) {
        super(message);
    }
}
