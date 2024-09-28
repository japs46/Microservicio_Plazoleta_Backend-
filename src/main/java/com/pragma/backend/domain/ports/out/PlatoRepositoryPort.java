package com.pragma.backend.domain.ports.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pragma.backend.domain.models.Plato;

public interface PlatoRepositoryPort {

	public Plato save(Plato plato);
	
	public Optional<Plato> findById(Long id);
	
	public Page<Plato> findByRestauranteEntityIdAndCategoria(Long restauranteId, String categoria, Pageable pageable);
	
	public Page<Plato> findByRestauranteEntityId(Long restauranteId, Pageable pageable);
}
