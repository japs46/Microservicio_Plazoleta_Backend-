package com.pragma.backend.infrastructure.mappers;

import com.pragma.backend.domain.models.DetallePedido;
import com.pragma.backend.infrastructure.entities.DetallePedidoEntity;

public class DetallePedidoMapper {

	public static DetallePedido toDomain(DetallePedidoEntity entity) {
        return new DetallePedido(entity.getId(),PlatoMapper.toDomain(entity.getPlato()),
        		null, entity.getCantidad());
    }

    public static DetallePedidoEntity toEntity(DetallePedido domain) {
        return new DetallePedidoEntity(domain.getId(),PlatoMapper.toEntity(domain.getPlato()),
        		null, domain.getCantidad());
    }
    
}
