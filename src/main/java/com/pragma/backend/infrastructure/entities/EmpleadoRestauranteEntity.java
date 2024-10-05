package com.pragma.backend.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name= "empleado_restaurante")
public class EmpleadoRestauranteEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "empleado_id",nullable = false)
    private final Long idEmpleado; 

    @ManyToOne
    @JoinColumn(name = "restaurante_id",nullable = false)
    private final RestauranteEntity restaurante;

	public EmpleadoRestauranteEntity(Long id, Long idEmpleado, RestauranteEntity restaurante) {
		this.id = id;
		this.idEmpleado = idEmpleado;
		this.restaurante = restaurante;
	}

	public EmpleadoRestauranteEntity() {
		this.id = null;
		this.idEmpleado = null;
		this.restaurante = new RestauranteEntity();
	}

	public Long getId() {
		return id;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public RestauranteEntity getRestaurante() {
		return restaurante;
	}
    
}
