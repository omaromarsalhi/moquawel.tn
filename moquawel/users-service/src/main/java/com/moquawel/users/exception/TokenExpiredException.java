package com.moquawel.users.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String invalidToken) {
        super(invalidToken);
    }
}
