package com.pragma.backend.application.usecases;

import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.ports.in.RetrieveRestauranteUseCase;
import com.pragma.backend.domain.ports.out.RestauranteRepositoryPort;

@Component
public class RetrieveRestauranteUseCaseImpl implements RetrieveRestauranteUseCase{
	
	private final RestauranteRepositoryPort restauranteRepositoryPort;

	public RetrieveRestauranteUseCaseImpl(RestauranteRepositoryPort restauranteRepositoryPort) {
		this.restauranteRepositoryPort = restauranteRepositoryPort;
	}

	@Override
	public Restaurante obtenerRestaurantePorIdPropietario(Long idPropietario) {
		
		if (idPropietario == null) {
	        throw new IllegalArgumentException("El ID del propietario no puede ser nulo.");
	    }
	    if (idPropietario <= 0) {
	        throw new IllegalArgumentException("El ID del propietario debe ser un número positivo.");
	    }
	    
		return restauranteRepositoryPort.findByIdPropietario(idPropietario).orElseThrow(()-> new NullPointerException("El usuario no es propietario de ningun restaurante."));
	}

	@Override
	public Restaurante obtenerRestaurantePorId(Long id) {
		
		if (id == null) {
	        throw new IllegalArgumentException("El ID no puede ser nulo.");
	    }
	    if (id <= 0) {
	        throw new IllegalArgumentException("El ID debe ser un número positivo.");
	    }

		return restauranteRepositoryPort.findById(id).orElseThrow(()-> new NullPointerException("No se encontro ningun restaurante con el id: "+id));
	}

}
