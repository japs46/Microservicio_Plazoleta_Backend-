package com.pragma.backend.domain.ports.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.models.RestauranteInfo;

public interface RestauranteRepositoryPort {

	public Restaurante save(Restaurante restaurante);
	
	public Optional<Restaurante> findById(Long id);
	
	public Optional<Restaurante> findByIdPropietario(Long id);
	
	public Page<RestauranteInfo> findAll(Pageable pageable);
}
