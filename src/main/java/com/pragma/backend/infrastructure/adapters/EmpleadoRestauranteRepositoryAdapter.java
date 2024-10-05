package com.pragma.backend.infrastructure.adapters;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.pragma.backend.domain.models.EmpleadoRestaurante;
import com.pragma.backend.domain.ports.out.EmpleadoRestauranteRepositoryPort;
import com.pragma.backend.infrastructure.entities.EmpleadoRestauranteEntity;
import com.pragma.backend.infrastructure.mappers.EmpleadoRestauranteMapper;
import com.pragma.backend.infrastructure.repositories.EmpleadoRestauranteEntityRepository;

@Repository
public class EmpleadoRestauranteRepositoryAdapter implements EmpleadoRestauranteRepositoryPort{
	
	private final EmpleadoRestauranteEntityRepository empleadoRestauranteEntityRepository;
	
	public EmpleadoRestauranteRepositoryAdapter(
			EmpleadoRestauranteEntityRepository empleadoRestauranteEntityRepository) {
		this.empleadoRestauranteEntityRepository = empleadoRestauranteEntityRepository;
	}

	@Override
	public EmpleadoRestaurante save(EmpleadoRestaurante empleadoRestaurante) {
		
		EmpleadoRestauranteEntity empleadoRestauranteEntity = EmpleadoRestauranteMapper.toEntity(empleadoRestaurante);

		return EmpleadoRestauranteMapper.toDomain(empleadoRestauranteEntityRepository.save(empleadoRestauranteEntity));
	}

	@Override
	public  Optional<EmpleadoRestaurante> findByIdEmpleado(Long idEmpleado) {
		return empleadoRestauranteEntityRepository.findByIdEmpleado(idEmpleado)
				.map(EmpleadoRestauranteMapper::toDomain);
	}

}
