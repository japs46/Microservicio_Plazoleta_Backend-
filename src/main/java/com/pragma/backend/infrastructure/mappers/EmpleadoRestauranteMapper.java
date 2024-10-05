package com.pragma.backend.infrastructure.mappers;

import com.pragma.backend.domain.models.EmpleadoRestaurante;
import com.pragma.backend.infrastructure.entities.EmpleadoRestauranteEntity;


public class EmpleadoRestauranteMapper {

	public static EmpleadoRestaurante toDomain(EmpleadoRestauranteEntity entity) {
        return new EmpleadoRestaurante(entity.getId(), entity.getIdEmpleado(), 
        		RestauranteMapper.toDomain(entity.getRestaurante()));
    }

    public static EmpleadoRestauranteEntity toEntity(EmpleadoRestaurante domain) {
        return new EmpleadoRestauranteEntity(domain.getId(),domain.getIdEmpleado(),
        		RestauranteMapper.toEntity(domain.getRestaurante()));
    }
}
