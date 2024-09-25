package com.pragma.backend.domain.ports.in;

import com.pragma.backend.domain.models.Restaurante;

public interface RetrieveRestauranteUseCase {

	public Restaurante obtenerRestaurantePorIdPropietario(Long idPropietario);
}
