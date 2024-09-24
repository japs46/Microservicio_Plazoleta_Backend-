package com.pragma.backend.infrastructure.mappers;

import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.infrastructure.entities.RestauranteEntity;

public class RestauranteMapper {

	public static Restaurante toDomain(RestauranteEntity entity) {
        return new Restaurante(entity.getId(), entity.getNombre(),
        		entity.getNit(), entity.getDireccion(),
        		entity.getTelefono(), entity.getUrlLogo(),
        		entity.getIdUsuarioPropietario());
    }

    public static RestauranteEntity toEntity(Restaurante domain) {
        return new RestauranteEntity(domain.getId(), domain.getNombre(),
        		domain.getNit(), domain.getDireccion(),
        		domain.getTelefono(), domain.getUrlLogo(),
        		domain.getIdUsuarioPropietario());
    }
    
}
