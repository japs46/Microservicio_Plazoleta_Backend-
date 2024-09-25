package com.pragma.backend.domain.ports.out;

import java.util.Optional;

import com.pragma.backend.domain.models.Restaurante;

public interface RestauranteRepositoryPort {

	public Restaurante save(Restaurante restaurante);
	
	public Optional<Restaurante> findById(Long id);
	
	public Optional<Restaurante> findByIdPropietario(Long id);
}
