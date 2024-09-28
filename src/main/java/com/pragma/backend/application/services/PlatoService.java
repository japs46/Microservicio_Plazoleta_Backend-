package com.pragma.backend.application.services;

import org.springframework.stereotype.Service;

import com.pragma.backend.domain.models.ModificarPlato;
import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.ports.in.CreatePlatoUseCase;
import com.pragma.backend.domain.ports.in.ModifyPlatoUseCase;
import com.pragma.backend.domain.ports.in.RetrievePlatoUseCase;

@Service
public class PlatoService implements CreatePlatoUseCase,ModifyPlatoUseCase,RetrievePlatoUseCase{

	private final CreatePlatoUseCase createPlatoUseCase;
	private final ModifyPlatoUseCase modifyPlatoUseCase;
	private final RetrievePlatoUseCase retrievePlatoUseCase;

	public PlatoService(CreatePlatoUseCase createPlatoUseCase, ModifyPlatoUseCase modifyPlatoUseCase,
			RetrievePlatoUseCase retrievePlatoUseCase) {
		this.createPlatoUseCase = createPlatoUseCase;
		this.modifyPlatoUseCase = modifyPlatoUseCase;
		this.retrievePlatoUseCase = retrievePlatoUseCase;
	}

	@Override
	public Plato createPlato(Plato plato,Long idUser) {
		return createPlatoUseCase.createPlato(plato,idUser);
	}

	@Override
	public Plato modifyPlato(ModificarPlato modificarPlato) {
		return modifyPlatoUseCase.modifyPlato(modificarPlato);
	}

	@Override
	public Plato obtenerPlatoPorId(Long id) {
		return retrievePlatoUseCase.obtenerPlatoPorId(id);
	}

}
