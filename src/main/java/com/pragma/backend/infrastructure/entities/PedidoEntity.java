package com.pragma.backend.infrastructure.entities;

import java.util.Date;
import java.util.List;

import com.pragma.backend.domain.models.EstadoPedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity(name="pedido")
public class PedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final Long id;
	
	@Column(name = "cliente_id", nullable = false)
	private final Long idCliente;
	
	@ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private final RestauranteEntity restaurante;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<DetallePedidoEntity> platos;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private final EstadoPedido estado;
	
	@Column(nullable = false,name = "fecha_pedido")
	private final Date fechaPedido;
	
	@Column(name = "empleado_id")
	private final Long idEmpleado;
	
	public PedidoEntity() {
		this.id = null;
		this.idCliente = null;
		this.restaurante = new RestauranteEntity();
		this.platos = null;
		this.estado = null;
		this.fechaPedido = new Date();
		this.idEmpleado = null;
	}

	public PedidoEntity(Long id, Long idCliente, RestauranteEntity restaurante, List<DetallePedidoEntity> platos,
			EstadoPedido estado, Date fechaPedido,Long idEmpleado) {
		this.id = id;
		this.idCliente = idCliente;
		this.restaurante = restaurante;
		this.platos = platos;
		this.estado = estado;
		this.fechaPedido = fechaPedido;
		this.idEmpleado = idEmpleado;
	}

	public Long getId() {
		return id;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public RestauranteEntity getRestaurante() {
		return restaurante;
	}

	public List<DetallePedidoEntity> getPlatos() {
		return platos;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}
	
	public Long getIdEmpleado() {
		return idEmpleado;
	}

	@PrePersist 
    private void asignarPedidoADetalles() {
        if (platos != null) {
            for (DetallePedidoEntity detalle : platos) {
                detalle.setPedido(this); 
            }
        }
    }
	
}
