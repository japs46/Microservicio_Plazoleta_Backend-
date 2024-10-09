package com.pragma.backend.application.usecases;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.DetallePedido;
import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.LogPedido;
import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.models.RequestPedido;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.ports.in.CreatePedidoUseCase;
import com.pragma.backend.domain.ports.in.RetrievePedidoUseCase;
import com.pragma.backend.domain.ports.in.RetrievePlatoUseCase;
import com.pragma.backend.domain.ports.in.RetrieveRestauranteUseCase;
import com.pragma.backend.domain.ports.out.PedidoRepositoryPort;
import com.pragma.backend.domain.ports.out.TrazabilidadExternalServicePort;

import jakarta.transaction.Transactional;

@Component
public class CreatePedidoUseCaseImpl implements CreatePedidoUseCase{
	
	private final PedidoRepositoryPort pedidoRepositoryPort;
	
	private final RetrieveRestauranteUseCase retrieveRestauranteUseCase;
	
	private final RetrievePlatoUseCase retrievePlatoUseCase;
	
	private final RetrievePedidoUseCase retrievePedidoUseCase; 
	
	private final TrazabilidadExternalServicePort trazabilidadExternalServicePort;
	
	public CreatePedidoUseCaseImpl(PedidoRepositoryPort pedidoRepositoryPort,
			@Qualifier("retrieveRestauranteUseCaseImpl") RetrieveRestauranteUseCase retrieveRestauranteUseCase,
			@Qualifier("retrievePlatoUseCaseImpl") RetrievePlatoUseCase retrievePlatoUseCase,
			@Qualifier("retrievePedidoUseCaseImpl") RetrievePedidoUseCase retrievePedidoUseCase,
			TrazabilidadExternalServicePort trazabilidadExternalServicePort) {
		this.pedidoRepositoryPort = pedidoRepositoryPort;
		this.retrieveRestauranteUseCase = retrieveRestauranteUseCase;
		this.retrievePlatoUseCase = retrievePlatoUseCase;
		this.retrievePedidoUseCase = retrievePedidoUseCase;
		this.trazabilidadExternalServicePort = trazabilidadExternalServicePort;
	}

	@Override
	@Transactional
	public Pedido createPedido(RequestPedido requestPedido, String token) {
	    
	    List<Pedido> listaPedidos = retrievePedidoUseCase.buscarPedidosPorCliente(requestPedido.getIdCliente());
	    validarPedidosEnProceso(listaPedidos);

	    validarCantidadMinimaPlatos(requestPedido.getPlatos().size());

	    Restaurante restaurante = retrieveRestauranteUseCase.obtenerRestaurantePorId(requestPedido.getIdRestaurante());

	    List<DetallePedido> platos = mapearYValidarPlatos(requestPedido, restaurante);

	    Pedido pedido = new Pedido(null, requestPedido.getIdCliente(), restaurante, platos, EstadoPedido.PENDIENTE, new Date(),null);
	    
	    Pedido pedidoGuardado = pedidoRepositoryPort.save(pedido);
	    
	    LogPedido logPedidoGuardado = trazabilidadExternalServicePort.guardarLog(crearLogPedido(pedidoGuardado), token);
	    
	    if (logPedidoGuardado==null) {
			throw new NoSuchElementException("Se interrumpio la operacion crear pedido debido a que no se pudo guardar el log del pedido");
		}
	    
	    return pedidoGuardado;
	}

	private void validarPedidosEnProceso(List<Pedido> listaPedidos) {
	    boolean tienePedidosEnProceso = listaPedidos.stream()
	        .anyMatch(pedido -> EnumSet.of(EstadoPedido.PENDIENTE, EstadoPedido.EN_PREPARACION, EstadoPedido.LISTO)
	            .contains(pedido.getEstado()));
	    
	    if (tienePedidosEnProceso) {
	        throw new RuntimeException("El cliente no puede realizar pedido porque tiene pedidos en proceso.");
	    }
	}

	private void validarCantidadMinimaPlatos(int cantidadPlatos) {
	    if (cantidadPlatos <= 0) {
	        throw new RuntimeException("La cantidad mínima requerida para realizar un pedido es de 1 plato.");
	    }
	}

	private List<DetallePedido> mapearYValidarPlatos(RequestPedido requestPedido, Restaurante restaurante) {
	    return requestPedido.getPlatos().stream()
	        .map(detalle -> {
	            validarCantidadPlato(detalle.getCantPlatos(), detalle.getIdPlato());
	            Plato plato = retrievePlatoUseCase.obtenerPlatoPorId(detalle.getIdPlato());
	            return new DetallePedido(null, plato, null, detalle.getCantPlatos());
	        })
	        .filter(detalle -> detalle.getPlato().getRestaurante().getId().equals(requestPedido.getIdRestaurante()))
	        .toList();
	}

	private void validarCantidadPlato(int cantPlatos, Long idPlato) {
	    if (cantPlatos <= 0) {
	        throw new IllegalArgumentException("La cantidad del plato debe ser un número positivo. Id del plato: " + idPlato);
	    }
	}

	private LogPedido crearLogPedido(Pedido pedido) {
		return new LogPedido(null, pedido.getId(), pedido.getIdCliente(),
				"NUEVO PEDIDO", pedido.getEstado().toString(), LocalDateTime.now());
	}
}
