package com.pragma.backend.application.usecases;

import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.ports.in.CreateRestauranteUseCase;
import com.pragma.backend.domain.ports.out.RestauranteRepositoryPort;

@Component
public class CreateRestauranteUseCaseImpl implements CreateRestauranteUseCase{
	
	private final RestauranteRepositoryPort restauranteRepositoryPort;
	
	public CreateRestauranteUseCaseImpl(RestauranteRepositoryPort restauranteRepositoryPort) {
		this.restauranteRepositoryPort = restauranteRepositoryPort;
	}

	@Override
	public Restaurante createRestaurante(Restaurante restaurante) {
		return restauranteRepositoryPort.save(restaurante);
	}

}
