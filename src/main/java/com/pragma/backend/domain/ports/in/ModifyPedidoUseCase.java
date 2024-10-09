package com.pragma.backend.domain.ports.in;

import com.pragma.backend.domain.models.Pedido;

public interface ModifyPedidoUseCase {

	public Pedido asignarPedido(Long id,Long idEmpleado,String token);
	
	public Pedido pedidoListo(Long id,String token);
	
	public Pedido pedidoEntregado(Long id,String token);
	
	public Pedido cancelarPedido(Long id, Long idCliente,String token);
}
