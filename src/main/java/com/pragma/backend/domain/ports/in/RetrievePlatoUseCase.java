package com.pragma.backend.domain.ports.in;

import org.springframework.data.domain.Page;

import com.pragma.backend.domain.models.Plato;

public interface RetrievePlatoUseCase {

	public Plato obtenerPlatoPorId(Long id);
	
	public Page<Plato> obtenerPlatosPorRestaurante(Long restauranteId, String categoria, int page, int size);
}
