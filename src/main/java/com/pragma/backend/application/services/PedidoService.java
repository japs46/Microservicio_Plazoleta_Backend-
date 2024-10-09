package com.pragma.backend.application.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.models.RequestPedido;
import com.pragma.backend.domain.ports.in.CreatePedidoUseCase;
import com.pragma.backend.domain.ports.in.ModifyPedidoUseCase;
import com.pragma.backend.domain.ports.in.RetrievePedidoUseCase;

@Service
public class PedidoService implements CreatePedidoUseCase,RetrievePedidoUseCase,ModifyPedidoUseCase{
	
	private final CreatePedidoUseCase createPedidoUseCase;
	
	private final RetrievePedidoUseCase retrievePedidoUseCase;
	
	private final ModifyPedidoUseCase modifyPedidoUseCase;
	
	public PedidoService(CreatePedidoUseCase createPedidoUseCase, RetrievePedidoUseCase retrievePedidoUseCase,
			ModifyPedidoUseCase modifyPedidoUseCase) {
		this.createPedidoUseCase = createPedidoUseCase;
		this.retrievePedidoUseCase = retrievePedidoUseCase;
		this.modifyPedidoUseCase = modifyPedidoUseCase;
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

	@Override
	public Pedido asignarPedido(Long id, Long idEmpleado,String token) {
		return modifyPedidoUseCase.asignarPedido(id, idEmpleado,token);
	}

	@Override
	public Pedido pedidoListo(Long id,String token) {
		return modifyPedidoUseCase.pedidoListo(id,token);
	}

	@Override
	public Pedido pedidoEntregado(Long id,String token) {
		return modifyPedidoUseCase.pedidoEntregado(id,token);
	}

	@Override
	public Pedido cancelarPedido(Long id,Long idCliente,String token) {
		return modifyPedidoUseCase.cancelarPedido(id,idCliente,token);
	}

}
