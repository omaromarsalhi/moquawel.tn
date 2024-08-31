package com.stage.authentification.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String invalidToken) {
        super(invalidToken);
    }
}
