package com.pragma.backend.infrastructure.adapters;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.ports.out.PlatoRepositoryPort;
import com.pragma.backend.infrastructure.entities.PlatoEntity;
import com.pragma.backend.infrastructure.mappers.PlatoMapper;
import com.pragma.backend.infrastructure.repositories.PlatoEntityRepository;

@Repository
public class PlatoRepositoryAdapter implements PlatoRepositoryPort{

	private final PlatoEntityRepository platoEntityRepository;
	
	public PlatoRepositoryAdapter(PlatoEntityRepository platoEntityRepository) {
		this.platoEntityRepository = platoEntityRepository;
	}

	@Override
	public Plato save(Plato plato) {
		PlatoEntity paltoEntity = PlatoMapper.toEntity(plato);
		return PlatoMapper.toDomain(platoEntityRepository.save(paltoEntity));
	}

	@Override
	public Optional<Plato> findById(Long id) {
		return platoEntityRepository.findById(id).map(PlatoMapper::toDomain);
	}

	@Override
	public Page<Plato> findByRestauranteEntityIdAndCategoria(Long restauranteId, String categoria, Pageable pageable) {
		return platoEntityRepository.findByRestauranteEntityIdAndCategoria(restauranteId, categoria, pageable)
				.map(PlatoMapper::toDomain);
	}

	@Override
	public Page<Plato> findByRestauranteEntityId(Long restauranteId, Pageable pageable) {
		return platoEntityRepository.findByRestauranteEntityId(restauranteId, pageable)
				.map(PlatoMapper::toDomain);
	}

}
