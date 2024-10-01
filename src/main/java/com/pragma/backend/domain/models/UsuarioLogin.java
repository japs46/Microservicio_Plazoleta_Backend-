package com.pragma.backend.domain.models;

public class UsuarioLogin {

	private final String correo;
	
	private final Rol rol;

	public UsuarioLogin(String correo, Rol rol) {
		this.correo = correo;
		this.rol = rol;
	}

	public String getCorreo() {
		return correo;
	}

	public Rol getRol() {
		return rol;
	}

}
