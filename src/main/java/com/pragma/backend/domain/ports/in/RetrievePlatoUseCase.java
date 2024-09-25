package com.pragma.backend.domain.ports.in;

import com.pragma.backend.domain.models.Plato;

public interface RetrievePlatoUseCase {

	public Plato obtenerPlatoPorId(Long id);
}
