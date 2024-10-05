package com.pragma.backend.domain.ports.in;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.Pedido;

public interface RetrievePedidoUseCase {

	public List<Pedido> buscarPedidosPorCliente(Long idCliente);
	
	public Page<Pedido> obtenerTodosPedidos(EstadoPedido estado,Long idEmpleado,int page,int size);
}
