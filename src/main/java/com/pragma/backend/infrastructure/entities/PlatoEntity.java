package com.pragma.backend.infrastructure.entities;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "plato")
public class PlatoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final Long id;

	@Column(nullable = false)
	private final String nombre;

	@Column(nullable = false)
	private final int precio;

	@Column(nullable = false)
	private final String descripcion;

	@Column(nullable = false,name = "url_imagen")
	private final String urlImagen;

	@Column(nullable = false)
	private final String categoria;
	
	@Column(nullable = false)
	@ColumnDefault("true") 
	private final boolean activo;
	
	@ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private RestauranteEntity restauranteEntity;
	
	public PlatoEntity() {
		this.id = null;
		this.nombre = "";
		this.precio = 0;
		this.descripcion = "";
		this.urlImagen = "";
		this.categoria = "";
		this.activo = false;
	}

	public PlatoEntity(Long id, String nombre, int precio, String descripcion, String urlImagen, String categoria,
			boolean activo, RestauranteEntity restaurante) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
		this.urlImagen = urlImagen;
		this.categoria = categoria;
		this.activo = activo;
		this.restauranteEntity = restaurante;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPrecio() {
		return precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public String getCategoria() {
		return categoria;
	}

	public boolean isActivo() {
		return activo;
	}

	public RestauranteEntity getRestauranteEntity() {
		return restauranteEntity;
	}
	
	
}
