package com.pragma.backend.application.response;

import java.util.List;

public class ErrorResponse {

	private final String message;
	private final List<Errores> errores;

	public ErrorResponse(String message, List<Errores> errores) {
		this.message = message;
		this.errores = errores;
	}

	public String getMessage() {
		return message;
	}

	public List<Errores> getErrores() {
		return errores;
	}

}
