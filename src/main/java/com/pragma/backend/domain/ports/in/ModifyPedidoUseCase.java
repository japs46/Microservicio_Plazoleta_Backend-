package com.pragma.backend.domain.ports.in;

import com.pragma.backend.domain.models.Pedido;

public interface ModifyPedidoUseCase {

	public Pedido asignarPedido(Long id,Long idEmpleado);
	
	public Pedido pedidoListo(Long id);
}
