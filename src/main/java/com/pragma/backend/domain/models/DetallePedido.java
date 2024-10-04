package com.pragma.backend.domain.models;

public class DetallePedido {

    private final Long id;

    private final Plato plato;

    private Pedido pedido;

    private final int cantidad;

	public DetallePedido() {
		this.id = null;
		this.pedido = null;
		this.plato = null;
		this.cantidad = 0;
	}

	public DetallePedido(Long id, Plato plato, Pedido pedido, int cantidad) {
		this.id = id;
		this.plato = plato;
		this.pedido = pedido;
		this.cantidad = cantidad;
	}

	public Long getId() {
		return id;
	}

	public Plato getPlato() {
		return plato;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
}
