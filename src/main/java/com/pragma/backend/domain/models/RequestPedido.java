package com.pragma.backend.domain.models;

import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RequestPedido {
	
	@Schema(hidden=true)
	private final Long id;
	
	@NotNull(message = "El ID del cliente no puede ser null")
	@Min(value = 1,message = "El ID del cliente debe ser un numero positivo")
    private final Long idCliente; 
	
	@NotNull(message = "El ID del restaurante no puede ser null")
	@Min(value = 1,message = "El ID del cliente debe ser un numero positivo")
    private final Long idRestaurante;
	
	@NotEmpty(message = "la lista de platos no puede ser null ni vacio.")
    private final List<DetallePedidoRequest> platos; 
    
	public RequestPedido(Long id, Long idCliente, Long idRestaurante, List<DetallePedidoRequest> platos, String estado,
			Date fechaPedido) {
		this.id = id;
		this.idCliente = idCliente;
		this.idRestaurante = idRestaurante;
		this.platos = platos;
	}

	public Long getId() {
		return id;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public Long getIdRestaurante() {
		return idRestaurante;
	}

	public List<DetallePedidoRequest> getPlatos() {
		return platos;
	}
	
}