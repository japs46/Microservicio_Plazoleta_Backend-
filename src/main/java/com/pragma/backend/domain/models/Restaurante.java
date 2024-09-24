package com.pragma.backend.domain.models;

public class Restaurante {
	
	private final Long id;

	private final String nombre;
	
    private final String nit;
    
    private final String direccion;
    
    private final String telefono;
    
    private final String urlLogo;
    
    private final Long idUsuarioPropietario;

	public Restaurante(Long id,String nombre, String nit, String direccion, String telefono, String urlLogo,
			Long idUsuarioPropietario) {
		this.id = id;
		this.nombre = nombre;
		this.nit = nit;
		this.direccion = direccion;
		this.telefono = telefono;
		this.urlLogo = urlLogo;
		this.idUsuarioPropietario = idUsuarioPropietario;
	}
	
	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNit() {
		return nit;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getUrlLogo() {
		return urlLogo;
	}

	public Long getIdUsuarioPropietario() {
		return idUsuarioPropietario;
	}
    
    
}
