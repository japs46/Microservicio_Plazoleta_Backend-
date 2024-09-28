package com.pragma.backend.domain.ports.in;

import org.springframework.data.domain.Page;

import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.models.RestauranteInfo;

public interface RetrieveRestauranteUseCase {
	
	public Restaurante obtenerRestaurantePorId(Long id);

	public Restaurante obtenerRestaurantePorIdPropietario(Long idPropietario);
	
	public Page<RestauranteInfo> obtenerTodosLosRestaurantes(int page, int size);
}
