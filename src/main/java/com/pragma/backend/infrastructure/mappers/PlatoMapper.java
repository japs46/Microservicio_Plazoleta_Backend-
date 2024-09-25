package com.pragma.backend.infrastructure.mappers;

import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.infrastructure.entities.PlatoEntity;

public class PlatoMapper {

	public static Plato toDomain(PlatoEntity entity) {
        return new Plato(entity.getId(), entity.getNombre(),
        		entity.getPrecio(), entity.getDescripcion(),
        		entity.getUrlImagen(), entity.getCategoria(),
        		entity.isActivo(),null,RestauranteMapper.toDomain(entity.getRestauranteEntity()));
    }

    public static PlatoEntity toEntity(Plato domain) {
        return new PlatoEntity(domain.getId(), domain.getNombre(),
        		domain.getPrecio(), domain.getDescripcion(),
        		domain.getUrlImagen(), domain.getCategoria(),
        		domain.isActivo(),RestauranteMapper.toEntity(domain.getRestaurante()));
    }
}
