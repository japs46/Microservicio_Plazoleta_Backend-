package com.pragma.backend.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pragma.backend.infrastructure.entities.EmpleadoRestauranteEntity;

@Repository
public interface EmpleadoRestauranteEntityRepository extends JpaRepository<EmpleadoRestauranteEntity, Long>{

	public  Optional<EmpleadoRestauranteEntity> findByIdEmpleado(Long idEmpleado);
}
