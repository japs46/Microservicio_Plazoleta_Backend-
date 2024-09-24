package com.pragma.backend.domain.exceptions;

public class UserNotOwnerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotOwnerException(String message) {
        super(message);
    }
}
