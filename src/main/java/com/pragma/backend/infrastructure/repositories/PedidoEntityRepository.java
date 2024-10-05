package com.pragma.backend.infrastructure.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.infrastructure.entities.PedidoEntity;

@Repository
public interface PedidoEntityRepository extends JpaRepository<PedidoEntity, Long>{

	public List<PedidoEntity> findByidCliente(Long idCliente);
	
	public Page<PedidoEntity> findAllByRestauranteIdAndEstado(Long idRestaurante,EstadoPedido estado,Pageable pageable);
}
