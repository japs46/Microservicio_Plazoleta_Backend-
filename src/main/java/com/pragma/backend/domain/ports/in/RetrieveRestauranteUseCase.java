package com.pragma.backend.domain.ports.in;

import com.pragma.backend.domain.models.Restaurante;

public interface RetrieveRestauranteUseCase {
	
	public Restaurante obtenerRestaurantePorId(Long id);

	public Restaurante obtenerRestaurantePorIdPropietario(Long idPropietario);
}
