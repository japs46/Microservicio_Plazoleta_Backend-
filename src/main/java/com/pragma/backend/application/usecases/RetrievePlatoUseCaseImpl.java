package com.pragma.backend.application.usecases;

import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.ports.in.RetrievePlatoUseCase;
import com.pragma.backend.domain.ports.out.PlatoRepositoryPort;

@Component
public class RetrievePlatoUseCaseImpl implements RetrievePlatoUseCase{
	
	private final PlatoRepositoryPort platoRepositoryPort;
	
	public RetrievePlatoUseCaseImpl(PlatoRepositoryPort platoRepositoryPort) {
		this.platoRepositoryPort = platoRepositoryPort;
	}

	@Override
	public Plato obtenerPlatoPorId(Long id) {
		return platoRepositoryPort.findById(id).orElseThrow();
	}

}
