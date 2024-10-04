package com.pragma.backend.infrastructure.mappers;

import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.infrastructure.entities.PedidoEntity;

public class PedidoMapper {

	public static Pedido toDomain(PedidoEntity entity) {
        return new Pedido(entity.getId(), entity.getIdCliente(),
        		RestauranteMapper.toDomain(entity.getRestaurante()), 
        		entity.getPlatos().stream().map(DetallePedidoMapper::toDomain).toList(),
        		entity.getEstado(), entity.getFechaPedido());
    }

    public static PedidoEntity toEntity(Pedido domain) {
        return new PedidoEntity(domain.getId(), domain.getIdCliente(),
        		RestauranteMapper.toEntity(domain.getRestaurante()), 
        		domain.getPlatos().stream().map(DetallePedidoMapper::toEntity).toList(),
        		domain.getEstado(), domain.getFechaPedido());
    }
}
