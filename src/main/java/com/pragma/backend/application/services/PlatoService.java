package com.pragma.backend.application.services;

import org.springframework.stereotype.Service;

import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.ports.in.CreatePlatoUseCase;

@Service
public class PlatoService implements CreatePlatoUseCase{

	private final CreatePlatoUseCase createPlatoUseCase;
	
	public PlatoService(CreatePlatoUseCase createPlatoUseCase) {
		this.createPlatoUseCase = createPlatoUseCase;
	}

	@Override
	public Plato createPlato(Plato plato) {
		return createPlatoUseCase.createPlato(plato);
	}

}
