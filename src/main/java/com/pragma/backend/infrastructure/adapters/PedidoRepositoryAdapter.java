package com.pragma.backend.infrastructure.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.pragma.backend.domain.models.EstadoPedido;
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

	@Override
	public Page<Pedido> findAll(Long idEmpleado,EstadoPedido estado,Pageable pageable) {
		return pedidoEntityRepository.findAllByRestauranteIdAndEstado(idEmpleado,estado,pageable)
				.map(PedidoMapper::toDomain);
	}

	@Override
	public Optional<Pedido> findById(Long id) {
		return pedidoEntityRepository.findById(id)
				.map(PedidoMapper::toDomain);
	}

}
