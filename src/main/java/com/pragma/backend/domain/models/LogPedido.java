package com.pragma.backend.domain.models;

import java.time.LocalDateTime;

public class LogPedido {

	private String id;
	private Long pedidoId;
	private Long clienteId;
	private String estadoAnterior;
	private String estadoNuevo;
	private LocalDateTime fechaCambio;

	public LogPedido(String id, Long pedidoId, Long clienteId, String estadoAnterior, String estadoNuevo,
			LocalDateTime fechaCambio) {
		this.id = id;
		this.pedidoId = pedidoId;
		this.clienteId = clienteId;
		this.estadoAnterior = estadoAnterior;
		this.estadoNuevo = estadoNuevo;
		this.fechaCambio = fechaCambio;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public LocalDateTime getFechaCambio() {
		return fechaCambio;
	}

	public String getId() {
		return id;
	}

}
