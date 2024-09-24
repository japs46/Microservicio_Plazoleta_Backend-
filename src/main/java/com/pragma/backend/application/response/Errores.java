package com.pragma.backend.application.response;

public class Errores {
	
	private final String campo;
	
	private final String errorMensaje;

	public Errores(String campo, String errorMensaje) {
		this.campo = campo;
		this.errorMensaje = errorMensaje;
	}

	public String getCampo() {
		return campo;
	}

	public String getErrorMensaje() {
		return errorMensaje;
	}

}
