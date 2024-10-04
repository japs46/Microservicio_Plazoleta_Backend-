package com.pragma.backend.infrastructure.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name="detalle_pedido")
public class DetallePedidoEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(hidden = true)
    private final Long id;

    @ManyToOne
    @JoinColumn(name = "plato_id")
    private final PlatoEntity plato;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @Schema(hidden = true)
    private PedidoEntity pedido;

    private final int cantidad;

	public DetallePedidoEntity() {
		this.id = null;
		this.plato = new PlatoEntity();
		this.pedido = null;
		this.cantidad = 0;
	}

	public DetallePedidoEntity(Long id, PlatoEntity plato, PedidoEntity pedido, int cantidad) {
		this.id = id;
		this.plato = plato;
		this.pedido = pedido;
		this.cantidad = cantidad;
	}

	public Long getId() {
		return id;
	}

	public PlatoEntity getPlato() {
		return plato;
	}

	public PedidoEntity getPedido() {
		return pedido;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setPedido(PedidoEntity pedido) {
		this.pedido = pedido;
	}
	
	
}
