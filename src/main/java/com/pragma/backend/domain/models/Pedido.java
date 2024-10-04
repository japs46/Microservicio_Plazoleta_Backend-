package com.pragma.backend.domain.models;

import java.util.Date;
import java.util.List;

public class Pedido {

	private final Long id;
    private final Long idCliente; 
    private final Restaurante restaurante; 
    private final List<DetallePedido> platos; 
    private final EstadoPedido estado;
    private final Date fechaPedido;
    
	public Pedido(Long id, Long idCliente, Restaurante restaurante, List<DetallePedido> platos, EstadoPedido estado,
			Date fechaPedido) {
		this.id = id;
		this.idCliente = idCliente;
		this.restaurante = restaurante;
		this.platos = platos;
		this.estado = estado;
		this.fechaPedido = fechaPedido;
	}

	public Long getId() {
		return id;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public List<DetallePedido> getPlatos() {
		return platos;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}
    
}
