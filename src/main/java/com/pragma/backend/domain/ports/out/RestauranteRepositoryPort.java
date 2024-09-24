package com.pragma.backend.domain.ports.out;

import com.pragma.backend.domain.models.Restaurante;

public interface RestauranteRepositoryPort {

	public Restaurante save(Restaurante restaurante);
}
