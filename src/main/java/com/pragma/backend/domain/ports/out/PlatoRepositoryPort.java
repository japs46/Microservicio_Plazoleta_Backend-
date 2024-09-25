package com.pragma.backend.domain.ports.out;

import java.util.Optional;

import com.pragma.backend.domain.models.Plato;

public interface PlatoRepositoryPort {

	public Plato save(Plato plato);
	
	public Optional<Plato> findById(Long id);
}
