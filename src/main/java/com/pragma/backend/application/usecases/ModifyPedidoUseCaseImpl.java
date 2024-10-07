package com.pragma.backend.application.usecases;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.EmpleadoRestaurante;
import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.ports.in.ModifyPedidoUseCase;
import com.pragma.backend.domain.ports.in.RetrieveEmpleadoRestauranteUseCase;
import com.pragma.backend.domain.ports.out.PedidoRepositoryPort;

@Component
public class ModifyPedidoUseCaseImpl implements ModifyPedidoUseCase{
	
	private final PedidoRepositoryPort pedidoRepositoryPort;
	
	private final RetrieveEmpleadoRestauranteUseCase retrieveEmpleadoRestauranteUseCase;
	
	public ModifyPedidoUseCaseImpl(PedidoRepositoryPort pedidoRepositoryPort,
			@Qualifier("retrieveEmpleadoRestauranteUseCaseImpl") RetrieveEmpleadoRestauranteUseCase retrieveEmpleadoRestauranteUseCase) {
		this.pedidoRepositoryPort = pedidoRepositoryPort;
		this.retrieveEmpleadoRestauranteUseCase = retrieveEmpleadoRestauranteUseCase;
	}

	@Override
	public Pedido asignarPedido(Long id, Long idEmpleado) {
		
		EmpleadoRestaurante empleadoRestauranteBd = retrieveEmpleadoRestauranteUseCase.obtenerEmpleadoRestaurantePorIdEmpleado(idEmpleado);
		
		Pedido pedidoBd = pedidoRepositoryPort.findById(id)
				.orElseThrow(()-> new NoSuchElementException("No se encontro ningun pedido con el id: "+id));
		
		if (pedidoBd.getEstado().equals(EstadoPedido.ENTREGADO)) {
			throw new IllegalArgumentException("Este pedido ya esta cerrado, por lo tanto no se puede asignar un empleado.");
		}
		
		if (empleadoRestauranteBd.getRestaurante().getId() != pedidoBd.getRestaurante().getId()) {
			throw new IllegalArgumentException("No puedes asignarte a pedidos de otro restaurante");
		}
		
		Pedido empleadoAsignadoPedido = new Pedido(pedidoBd.getId(), pedidoBd.getIdCliente(),
				pedidoBd.getRestaurante(), pedidoBd.getPlatos(), EstadoPedido.EN_PREPARACION,
				pedidoBd.getFechaPedido(), idEmpleado);
		
		return pedidoRepositoryPort.save(empleadoAsignadoPedido);
	}

	@Override
	public Pedido pedidoListo(Long id) {
		
		Pedido pedidoBd = pedidoRepositoryPort.findById(id)
				.orElseThrow(()-> new NoSuchElementException("No se encontro ningun pedido con el id: "+id));

		Pedido pedidoListo = new Pedido(pedidoBd.getId(), pedidoBd.getIdCliente(),
				pedidoBd.getRestaurante(), pedidoBd.getPlatos(), EstadoPedido.LISTO,
				pedidoBd.getFechaPedido(), pedidoBd.getIdEmpleado());
		
		return pedidoRepositoryPort.save(pedidoListo);
	}

	@Override
	public Pedido pedidoEntregado(Long id) {
		
		Pedido pedidoBd = pedidoRepositoryPort.findById(id)
				.orElseThrow(()-> new NoSuchElementException("No se encontro ningun pedido con el id: "+id));
		
		if (!pedidoBd.getEstado().equals(EstadoPedido.LISTO)) {
			throw new RuntimeException("Solo los pedidos que se encuentren en estado listo pueden pasar a estado entregado.");
		}

		Pedido pedidoEntregado = new Pedido(pedidoBd.getId(), pedidoBd.getIdCliente(),
				pedidoBd.getRestaurante(), pedidoBd.getPlatos(), EstadoPedido.ENTREGADO,
				pedidoBd.getFechaPedido(), pedidoBd.getIdEmpleado());
		
		return pedidoRepositoryPort.save(pedidoEntregado);
	}

	@Override
	public Pedido cancelarPedido(Long id, Long idCliente) {
		
		Pedido pedidoBd = pedidoRepositoryPort.findById(id)
				.orElseThrow(()-> new NoSuchElementException("No se encontro ningun pedido con el id: "+id));
		
		if (pedidoBd.getIdCliente() != idCliente) {
			throw new IllegalArgumentException("El pedido que quieres cancelar pertenece a otro cliente.");
		}
		
		if (!pedidoBd.getEstado().equals(EstadoPedido.PENDIENTE)) {
			throw new RuntimeException("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse.");
		}

		Pedido pedidoCancelado = new Pedido(pedidoBd.getId(), pedidoBd.getIdCliente(),
				pedidoBd.getRestaurante(), pedidoBd.getPlatos(), EstadoPedido.CANCELADO,
				pedidoBd.getFechaPedido(), pedidoBd.getIdEmpleado());
		
		return pedidoRepositoryPort.save(pedidoCancelado);
	}

}
