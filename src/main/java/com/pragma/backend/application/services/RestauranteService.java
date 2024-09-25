package com.pragma.backend.application.services;

import org.springframework.stereotype.Service;

import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.ports.in.CreateRestauranteUseCase;
import com.pragma.backend.domain.ports.in.RetrieveRestauranteUseCase;

@Service
public class RestauranteService implements CreateRestauranteUseCase,RetrieveRestauranteUseCase{
	
	private final CreateRestauranteUseCase createRestauranteUseCase;
	private final RetrieveRestauranteUseCase retrieveRestauranteUseCase;

	public RestauranteService(CreateRestauranteUseCase createRestauranteUseCase,
			RetrieveRestauranteUseCase retrieveRestauranteUseCase) {
		this.createRestauranteUseCase = createRestauranteUseCase;
		this.retrieveRestauranteUseCase = retrieveRestauranteUseCase;
	}

	@Override
	public Restaurante createRestaurante(Restaurante restaurante) {
		return createRestauranteUseCase.createRestaurante(restaurante);
	}

	@Override
	public Restaurante obtenerRestaurantePorIdPropietario(Long idPropietario) {
		return retrieveRestauranteUseCase.obtenerRestaurantePorIdPropietario(idPropietario);
	}

}
