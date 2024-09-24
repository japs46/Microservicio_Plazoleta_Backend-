package com.pragma.backend.infrastructure.adapters;

import org.springframework.stereotype.Repository;

import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.ports.out.RestauranteRepositoryPort;
import com.pragma.backend.infrastructure.entities.RestauranteEntity;
import com.pragma.backend.infrastructure.mappers.RestauranteMapper;
import com.pragma.backend.infrastructure.repositories.RestauranteEntityRepository;

@Repository
public class RestauranteRepositoryAdapter implements RestauranteRepositoryPort{
	
	private final RestauranteEntityRepository restauranteEntityRepository;
	
	public RestauranteRepositoryAdapter(RestauranteEntityRepository restauranteEntityRepository) {
		this.restauranteEntityRepository = restauranteEntityRepository;
	}

	@Override
	public Restaurante save(Restaurante restaurante) {
		RestauranteEntity restauranteEntity = RestauranteMapper.toEntity(restaurante);
		return RestauranteMapper.toDomain(restauranteEntityRepository.save(restauranteEntity));
	}

}
