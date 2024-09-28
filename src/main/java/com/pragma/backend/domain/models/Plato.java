package com.pragma.backend.domain.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Plato {

	@Schema(hidden = true)
	private final Long id;
	
	@NotEmpty(message = "El nombre no puede ser vacio")
	private final String nombre;
	
	@Min(value = 1 ,message = "El precio debe ser un número entero positivo mayor que 0")
	private final int precio;
	
	@NotEmpty(message = "La descripcion no puede ser vacio")
	private final String descripcion;
	
	@NotEmpty(message = "La Url de la imagen no puede ser vacio")
	private final String urlImagen;
	
	@NotEmpty(message = "La Categoria no puede ser vacio")
	private final String categoria;
	
	@NotNull(message = "ID del restaurante no puede ser null")
	@Min(value = 1,message = "El ID debe ser un número positivo.")
	private final Long idRestaurante;
	
	@Schema(hidden = true)
	private final boolean activo;
	
	@Schema(hidden = true)
	private final Restaurante restaurante;

	public Plato(Long id, String nombre, int precio,String descripcion,String urlImagen,
			String categoria, Long idRestaurante,boolean activo, Restaurante restaurante) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
		this.urlImagen = urlImagen;
		this.categoria = categoria;
		this.idRestaurante = idRestaurante;
		this.activo = activo;
		this.restaurante = restaurante;
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

	public Long getIdRestaurante() {
		return idRestaurante;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public boolean isActivo() {
		return activo;
	}
	
}
