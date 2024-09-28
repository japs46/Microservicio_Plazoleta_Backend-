package com.pragma.backend.application.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.models.RestauranteInfo;
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
	public Restaurante createRestaurante(Restaurante restaurante,String token) {
		return createRestauranteUseCase.createRestaurante(restaurante,token);
	}

	@Override
	public Restaurante obtenerRestaurantePorIdPropietario(Long idPropietario) {
		return retrieveRestauranteUseCase.obtenerRestaurantePorIdPropietario(idPropietario);
	}

	@Override
	public Restaurante obtenerRestaurantePorId(Long id) {
		return retrieveRestauranteUseCase.obtenerRestaurantePorId(id);
	}

	@Override
	public Page<RestauranteInfo> obtenerTodosLosRestaurantes(int page, int size) {
		return retrieveRestauranteUseCase.obtenerTodosLosRestaurantes(page,size);
	}

}
