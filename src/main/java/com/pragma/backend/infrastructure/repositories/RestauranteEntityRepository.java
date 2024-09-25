package com.pragma.backend.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pragma.backend.infrastructure.entities.RestauranteEntity;

@Repository
public interface RestauranteEntityRepository extends JpaRepository<RestauranteEntity, Long>{

	public Optional<RestauranteEntity> findByIdUsuarioPropietario(Long idPropietario);
}
