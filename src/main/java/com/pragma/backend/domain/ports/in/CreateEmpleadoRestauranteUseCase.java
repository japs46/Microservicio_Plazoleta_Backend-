package com.pragma.backend.domain.ports.in;

import com.pragma.backend.domain.models.EmpleadoRestaurante;

public interface CreateEmpleadoRestauranteUseCase {

	public EmpleadoRestaurante createEmpleadoRestaurante(Long idEmpleado,Long idPropietario);
}
