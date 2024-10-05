package com.pragma.backend.domain.ports.out;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.Pedido;

public interface PedidoRepositoryPort {

	public Pedido save(Pedido pedido);
	
	public List<Pedido> findByidCliente(Long idCliente);
	
	public Page<Pedido> findAll(Long idEmpleado,EstadoPedido estado,Pageable pageable);
}
