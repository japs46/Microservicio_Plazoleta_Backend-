package com.pragma.backend.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.ports.in.RetrievePedidoUseCase;
import com.pragma.backend.domain.ports.out.PedidoRepositoryPort;

@Component
public class RetrievePedidoUseCaseImpl implements RetrievePedidoUseCase{
	
	private final PedidoRepositoryPort pedidoRepositoryPort;
	
	public RetrievePedidoUseCaseImpl(PedidoRepositoryPort pedidoRepositoryPort) {
		this.pedidoRepositoryPort = pedidoRepositoryPort;
	}

	@Override
	public List<Pedido> buscarPedidosPorCliente(Long idCliente) {
		
		if (idCliente == null) {
	        throw new IllegalArgumentException("El ID del cliente no puede ser nulo.");
	    }
	    if (idCliente <= 0) {
	        throw new IllegalArgumentException("El ID del cliente debe ser un número positivo.");
	    }
		
		return pedidoRepositoryPort.findByidCliente(idCliente);
	}

}
