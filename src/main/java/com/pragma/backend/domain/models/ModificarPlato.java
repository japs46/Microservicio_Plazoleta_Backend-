package com.pragma.backend.domain.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ModificarPlato {
	
	@NotNull(message = "El ID no puede ser vacio.")
	@Min(value = 1 ,message = "El ID debe ser un número entero positivo mayor que 0")
	private final Long id;

	@Min(value = 1 ,message = "El precio debe ser un número entero positivo mayor que 0")
	private final int precio;
	
	@NotEmpty(message = "La descripcion no puede ser vacio")
	private final String descripcion;

	public ModificarPlato(Long id, int precio, String descripcion) {
		this.id = id;
		this.precio = precio;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public int getPrecio() {
		return precio;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
}
