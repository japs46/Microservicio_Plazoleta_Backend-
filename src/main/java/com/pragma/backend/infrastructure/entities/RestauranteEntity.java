package com.pragma.backend.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurante")
public class RestauranteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final Long id;

	@Column(nullable = false)
	private final String nombre;
	
	@Column(nullable = false)
    private final String nit;
    
	@Column(nullable = false)
    private final String direccion;
    
	@Column(length = 13, nullable = false)
    private final String telefono;
    
    @Column(nullable = false, name = "url_logo")
    private final String urlLogo;
    
    @Column(nullable = false,name = "id_usuario_propietario")
    private final Long idUsuarioPropietario;

	public RestauranteEntity() {
		this.id = null;
		this.nombre = "";
		this.nit = "";
		this.direccion = "";
		this.telefono = "";
		this.urlLogo = "";
		this.idUsuarioPropietario = null;
	}

	public RestauranteEntity(Long id, String nombre, String nit, String direccion, String telefono, String urlLogo,
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
