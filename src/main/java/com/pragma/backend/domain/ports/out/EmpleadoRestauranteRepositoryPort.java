package com.pragma.backend.domain.ports.out;

import java.util.Optional;

import com.pragma.backend.domain.models.EmpleadoRestaurante;

public interface EmpleadoRestauranteRepositoryPort {

	public EmpleadoRestaurante save(EmpleadoRestaurante empleadoRestaurante);
	
	public Optional<EmpleadoRestaurante> findByIdEmpleado(Long idEmpleado);
}
