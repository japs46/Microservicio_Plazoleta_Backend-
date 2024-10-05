package com.pragma.backend.application.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.EmpleadoRestaurante;
import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.ports.in.RetrieveEmpleadoRestauranteUseCase;
import com.pragma.backend.domain.ports.in.RetrievePedidoUseCase;
import com.pragma.backend.domain.ports.out.PedidoRepositoryPort;

@Component
public class RetrievePedidoUseCaseImpl implements RetrievePedidoUseCase{
	
	private final PedidoRepositoryPort pedidoRepositoryPort;
	
	private final RetrieveEmpleadoRestauranteUseCase retrieveEmpleadoRestauranteUseCase;
	
	public RetrievePedidoUseCaseImpl(PedidoRepositoryPort pedidoRepositoryPort,
			@Qualifier("retrieveEmpleadoRestauranteUseCaseImpl") RetrieveEmpleadoRestauranteUseCase retrieveEmpleadoRestauranteUseCase) {
		this.pedidoRepositoryPort = pedidoRepositoryPort;
		this.retrieveEmpleadoRestauranteUseCase = retrieveEmpleadoRestauranteUseCase;
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

	@Override
	public Page<Pedido> obtenerTodosPedidos(EstadoPedido estado, Long idEmpleado, int page, int size) {
		
		EmpleadoRestaurante empleadoRestauranteBd = retrieveEmpleadoRestauranteUseCase.obtenerEmpleadoRestaurantePorIdEmpleado(idEmpleado);

		if (estado == null) {
			throw new IllegalArgumentException("El estado no puede ser nulo.");
		}
		
		Pageable pageable = PageRequest.of(page, size);

		return pedidoRepositoryPort.findAll(empleadoRestauranteBd.getRestaurante().getId(),estado, pageable);
	}

}
