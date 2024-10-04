package com.pragma.backend.infrastructure.adapters;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.ports.out.PedidoRepositoryPort;
import com.pragma.backend.infrastructure.entities.PedidoEntity;
import com.pragma.backend.infrastructure.mappers.PedidoMapper;
import com.pragma.backend.infrastructure.repositories.PedidoEntityRepository;

@Repository
public class PedidoRepositoryAdapter implements PedidoRepositoryPort{

	private final PedidoEntityRepository pedidoEntityRepository;
	
	public PedidoRepositoryAdapter(PedidoEntityRepository pedidoEntityRepository) {
		this.pedidoEntityRepository = pedidoEntityRepository;
	}

	@Override
	public Pedido save(Pedido pedido) {
		PedidoEntity pedidoEntity = PedidoMapper.toEntity(pedido);
		return PedidoMapper.toDomain(pedidoEntityRepository.save(pedidoEntity));
	}

	@Override
	public List<Pedido> findByidCliente(Long idCliente) {
		return pedidoEntityRepository.findByidCliente(idCliente).stream()
				.map(PedidoMapper::toDomain).toList();
	}

}
