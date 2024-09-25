package com.pragma.backend.domain.ports.out;

import com.pragma.backend.domain.models.Plato;

public interface PlatoRepositoryPort {

	public Plato save(Plato plato);
}
