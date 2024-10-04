package com.pragma.backend.domain.ports.in;

import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.models.RequestPedido;

public interface CreatePedidoUseCase {

	public Pedido createPedido(RequestPedido requestPedido,String token);
}
