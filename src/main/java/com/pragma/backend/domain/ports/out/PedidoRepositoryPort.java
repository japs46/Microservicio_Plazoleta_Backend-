package com.pragma.backend.domain.ports.out;

import java.util.List;

import com.pragma.backend.domain.models.Pedido;

public interface PedidoRepositoryPort {

	public Pedido save(Pedido pedido);
	
	 List<Pedido> findByidCliente(Long idCliente);
}
