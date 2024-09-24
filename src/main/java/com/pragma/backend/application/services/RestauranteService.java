package com.pragma.backend.application.services;

import org.springframework.stereotype.Service;

import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.ports.in.CreateRestauranteUseCase;

@Service
public class RestauranteService implements CreateRestauranteUseCase{
	
	private final CreateRestauranteUseCase createRestauranteUseCase;
	
	public RestauranteService(CreateRestauranteUseCase createRestauranteUseCase) {
		this.createRestauranteUseCase = createRestauranteUseCase;
	}

	@Override
	public Restaurante createRestaurante(Restaurante restaurante) {
		return createRestauranteUseCase.createRestaurante(restaurante);
	}

}
