package com.pragma.backend.domain.ports.in;

import com.pragma.backend.domain.models.EmpleadoRestaurante;

public interface RetrieveEmpleadoRestauranteUseCase {

	public EmpleadoRestaurante obtenerEmpleadoRestaurantePorIdEmpleado(Long idEmpleado);
}
