package com.pragma.backend.domain.models;

public class EmpleadoRestaurante {

    private final Long id;

    private final Long idEmpleado; 

    private final Restaurante restaurante;

	public EmpleadoRestaurante(Long id, Long idEmpleado, Restaurante restaurante) {
		this.id = id;
		this.idEmpleado = idEmpleado;
		this.restaurante = restaurante;
	}

	public EmpleadoRestaurante() {
		this.id = null;
		this.idEmpleado = null;
		this.restaurante = null;
	}

	public Long getId() {
		return id;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}
}
