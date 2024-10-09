package com.pragma.backend.application.usecases;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.EmpleadoRestaurante;
import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.LogPedido;
import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.ports.in.ModifyPedidoUseCase;
import com.pragma.backend.domain.ports.in.RetrieveEmpleadoRestauranteUseCase;
import com.pragma.backend.domain.ports.out.PedidoRepositoryPort;
import com.pragma.backend.domain.ports.out.TrazabilidadExternalServicePort;

import jakarta.transaction.Transactional;

@Component
public class ModifyPedidoUseCaseImpl implements ModifyPedidoUseCase{
	
	private final PedidoRepositoryPort pedidoRepositoryPort;
	
	private final RetrieveEmpleadoRestauranteUseCase retrieveEmpleadoRestauranteUseCase;
	
	private final TrazabilidadExternalServicePort trazabilidadExternalServicePort;
	
	public ModifyPedidoUseCaseImpl(PedidoRepositoryPort pedidoRepositoryPort,
			@Qualifier("retrieveEmpleadoRestauranteUseCaseImpl") RetrieveEmpleadoRestauranteUseCase retrieveEmpleadoRestauranteUseCase,
			TrazabilidadExternalServicePort trazabilidadExternalServicePort) {
		this.pedidoRepositoryPort = pedidoRepositoryPort;
		this.retrieveEmpleadoRestauranteUseCase = retrieveEmpleadoRestauranteUseCase;
		this.trazabilidadExternalServicePort = trazabilidadExternalServicePort;
	}

	@Override
	@Transactional
	public Pedido asignarPedido(Long id, Long idEmpleado,String token) {
		
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
		
		LogPedido logPedido=crearLogPedido(empleadoAsignadoPedido,pedidoBd.getEstado().toString());
		
		LogPedido logPedidoGuardado = trazabilidadExternalServicePort.guardarLog(logPedido, token);
	    
	    if (logPedidoGuardado==null) {
			throw new NoSuchElementException("Se interrumpio la operacion asignar empleado a pedido debido a que no se pudo guardar el log del pedido");
		}
		
		return pedidoRepositoryPort.save(empleadoAsignadoPedido);
	}

	@Override
	@Transactional
	public Pedido pedidoListo(Long id,String token) {
		
		Pedido pedidoBd = pedidoRepositoryPort.findById(id)
				.orElseThrow(()-> new NoSuchElementException("No se encontro ningun pedido con el id: "+id));

		Pedido pedidoListo = new Pedido(pedidoBd.getId(), pedidoBd.getIdCliente(),
				pedidoBd.getRestaurante(), pedidoBd.getPlatos(), EstadoPedido.LISTO,
				pedidoBd.getFechaPedido(), pedidoBd.getIdEmpleado());
		
		LogPedido logPedido=crearLogPedido(pedidoListo,pedidoBd.getEstado().toString());
		
		LogPedido logPedidoGuardado = trazabilidadExternalServicePort.guardarLog(logPedido, token);
		
		if (logPedidoGuardado==null) {
			throw new NoSuchElementException("Se interrumpio la operacion cambiar estado pedido listo debido a que no se pudo guardar el log del pedido");
		}
		
		return pedidoRepositoryPort.save(pedidoListo);
	}

	@Override
	@Transactional
	public Pedido pedidoEntregado(Long id,String token) {
		
		Pedido pedidoBd = pedidoRepositoryPort.findById(id)
				.orElseThrow(()-> new NoSuchElementException("No se encontro ningun pedido con el id: "+id));
		
		if (!pedidoBd.getEstado().equals(EstadoPedido.LISTO)) {
			throw new RuntimeException("Solo los pedidos que se encuentren en estado listo pueden pasar a estado entregado.");
		}

		Pedido pedidoEntregado = new Pedido(pedidoBd.getId(), pedidoBd.getIdCliente(),
				pedidoBd.getRestaurante(), pedidoBd.getPlatos(), EstadoPedido.ENTREGADO,
				pedidoBd.getFechaPedido(), pedidoBd.getIdEmpleado());
		
		LogPedido logPedido=crearLogPedido(pedidoEntregado,pedidoBd.getEstado().toString());
		
		LogPedido logPedidoGuardado = trazabilidadExternalServicePort.guardarLog(logPedido, token);
		
		if (logPedidoGuardado==null) {
			throw new NoSuchElementException("Se interrumpio la operacion cambiar estado pedido entregado debido a que no se pudo guardar el log del pedido");
		}
		
		return pedidoRepositoryPort.save(pedidoEntregado);
	}

	@Override
	@Transactional
	public Pedido cancelarPedido(Long id, Long idCliente,String token) {
		
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
		
		LogPedido logPedido=crearLogPedido(pedidoCancelado,pedidoBd.getEstado().toString());
		
		LogPedido logPedidoGuardado = trazabilidadExternalServicePort.guardarLog(logPedido, token);
		
		if (logPedidoGuardado==null) {
			throw new NoSuchElementException("Se interrumpio la operacion cambiar estado pedido cancelado debido a que no se pudo guardar el log del pedido");
		}
		
		return pedidoRepositoryPort.save(pedidoCancelado);
	}
	
	private LogPedido crearLogPedido(Pedido pedido,String estadoAnterior) {
		return new LogPedido(null, pedido.getId(), pedido.getIdCliente(),
				estadoAnterior, pedido.getEstado().toString(), LocalDateTime.now());
	}

}
