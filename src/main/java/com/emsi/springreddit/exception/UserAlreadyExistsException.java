package com.emsi.springreddit.exception;

import java.sql.SQLIntegrityConstraintViolationException;

public class UserAlreadyExistsException extends SQLIntegrityConstraintViolationException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
