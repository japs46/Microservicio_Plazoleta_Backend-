package com.pragma.backend.domain.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DetallePedidoRequest {

	@NotNull(message = "el ID del plato no puede ser null")
	@Min(value = 1, message = "el ID del plato debe ser un numero positivo")
	private final Long idPlato;
	
	@Min(value = 1, message = "la cantidad de platos debe ser un numero positivo")
	private final int cantPlatos;
	
	public DetallePedidoRequest(Long idPlato, int cantPlatos) {
		this.idPlato = idPlato;
		this.cantPlatos = cantPlatos;
	}

	public Long getIdPlato() {
		return idPlato;
	}

	public int getCantPlatos() {
		return cantPlatos;
	}
}
