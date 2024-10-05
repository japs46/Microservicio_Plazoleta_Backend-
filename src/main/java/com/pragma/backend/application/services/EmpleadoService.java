package com.pragma.backend.application.services;

import org.springframework.stereotype.Service;

import com.pragma.backend.domain.models.EmpleadoRestaurante;
import com.pragma.backend.domain.ports.in.CreateEmpleadoRestauranteUseCase;

@Service
public class EmpleadoService implements CreateEmpleadoRestauranteUseCase{
	
	private final CreateEmpleadoRestauranteUseCase createEmpleadoRestauranteUseCase;
	
	public EmpleadoService(CreateEmpleadoRestauranteUseCase createEmpleadoRestauranteUseCase) {
		this.createEmpleadoRestauranteUseCase = createEmpleadoRestauranteUseCase;
	}

	@Override
	public EmpleadoRestaurante createEmpleadoRestaurante(Long idEmpleado,Long idPropietario) {
		return createEmpleadoRestauranteUseCase.createEmpleadoRestaurante(idEmpleado, idPropietario);
	}

}
