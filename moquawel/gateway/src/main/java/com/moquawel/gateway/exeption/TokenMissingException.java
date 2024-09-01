package com.moquawel.gateway.exeption;

public class TokenMissingException extends RuntimeException {
    public TokenMissingException(String invalidToken) {
        super(invalidToken);
    }
}
