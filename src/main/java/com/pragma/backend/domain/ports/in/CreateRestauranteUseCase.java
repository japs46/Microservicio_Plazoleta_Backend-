package com.pragma.backend.domain.ports.in;

import com.pragma.backend.domain.models.Restaurante;

public interface CreateRestauranteUseCase {

	public Restaurante createRestaurante(Restaurante restaurante,String token);
}
