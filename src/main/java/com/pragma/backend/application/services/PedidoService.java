package com.pragma.backend.application.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.models.RequestPedido;
import com.pragma.backend.domain.ports.in.CreatePedidoUseCase;
import com.pragma.backend.domain.ports.in.RetrievePedidoUseCase;

@Service
public class PedidoService implements CreatePedidoUseCase,RetrievePedidoUseCase{
	
	private final CreatePedidoUseCase createPedidoUseCase;
	
	private final RetrievePedidoUseCase retrievePedidoUseCase;
	
	public PedidoService(CreatePedidoUseCase createPedidoUseCase,RetrievePedidoUseCase retrievePedidoUseCase) {
		this.createPedidoUseCase = createPedidoUseCase;
		this.retrievePedidoUseCase = retrievePedidoUseCase;
	}

	@Override
	public Pedido createPedido(RequestPedido requestPedido,String token) {
		return createPedidoUseCase.createPedido(requestPedido,token);
	}

	@Override
	public List<Pedido> buscarPedidosPorCliente(Long idCliente) {
		return retrievePedidoUseCase.buscarPedidosPorCliente(idCliente);
	}

	@Override
	public Page<Pedido> obtenerTodosPedidos(EstadoPedido estado,Long idEmpleado, int page, int size) {
		return retrievePedidoUseCase.obtenerTodosPedidos(estado,idEmpleado, page, size);
	}

}
