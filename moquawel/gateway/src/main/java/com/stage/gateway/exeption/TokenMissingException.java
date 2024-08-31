package com.stage.gateway.exeption;

public class TokenMissingException extends RuntimeException {
    public TokenMissingException(String invalidToken) {
        super(invalidToken);
    }
}
