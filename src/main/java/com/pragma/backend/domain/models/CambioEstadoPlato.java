package com.pragma.backend.domain.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CambioEstadoPlato {

	@NotNull(message = "El ID no puede ser vacio.")
	@Min(value = 1, message = "El ID debe ser un numero positivo.")
	private final Long id;
	
	private final boolean activo;

	public CambioEstadoPlato(Long id, boolean activo) {
		this.id = id;
		this.activo = activo;
	}

	public Long getId() {
		return id;
	}

	public boolean isActivo() {
		return activo;
	}
	
	
}
