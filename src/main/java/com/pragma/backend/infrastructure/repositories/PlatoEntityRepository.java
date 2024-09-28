package com.pragma.backend.infrastructure.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pragma.backend.infrastructure.entities.PlatoEntity;

@Repository
public interface PlatoEntityRepository extends JpaRepository<PlatoEntity, Long>{

    Page<PlatoEntity> findByRestauranteEntityIdAndCategoria(Long restauranteId, String categoria, Pageable pageable);
	
    Page<PlatoEntity> findByRestauranteEntityId(Long restauranteId, Pageable pageable);
}
