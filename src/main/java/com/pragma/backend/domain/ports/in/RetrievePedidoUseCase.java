package com.pragma.backend.domain.ports.in;

import java.util.List;

import com.pragma.backend.domain.models.Pedido;

public interface RetrievePedidoUseCase {

	public List<Pedido> buscarPedidosPorCliente(Long idCliente);
}
