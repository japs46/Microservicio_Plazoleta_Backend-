package com.pragma.backend.domain.models;

public class RestauranteInfo {

	private final String nombre;
	
	private final String urlLogo;

	public RestauranteInfo(String nombre, String urlLogo) {
		this.nombre = nombre;
		this.urlLogo = urlLogo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getUrlLogo() {
		return urlLogo;
	}
	
	
}
