package com.pragma.backend.application.usecases;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.EmpleadoRestaurante;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.ports.in.CreateEmpleadoRestauranteUseCase;
import com.pragma.backend.domain.ports.in.RetrieveRestauranteUseCase;
import com.pragma.backend.domain.ports.out.EmpleadoRestauranteRepositoryPort;

@Component
public class CreateEmpleadoRestauranteUseCaseImpl implements CreateEmpleadoRestauranteUseCase{
	
	private final EmpleadoRestauranteRepositoryPort empleadoRestauranteRepositoryPort;
	
	private final RetrieveRestauranteUseCase retrieveRestauranteUseCase;
	
	public CreateEmpleadoRestauranteUseCaseImpl(EmpleadoRestauranteRepositoryPort empleadoRestauranteRepositoryPort,
			@Qualifier("retrieveRestauranteUseCaseImpl") RetrieveRestauranteUseCase retrieveRestauranteUseCase) {
		this.empleadoRestauranteRepositoryPort = empleadoRestauranteRepositoryPort;
		this.retrieveRestauranteUseCase = retrieveRestauranteUseCase;
	}

	@Override
	public EmpleadoRestaurante createEmpleadoRestaurante(Long idEmpleado, Long idPropietario) {
		
		Restaurante restauranteBd= retrieveRestauranteUseCase.obtenerRestaurantePorIdPropietario(idPropietario);
		
		EmpleadoRestaurante empleadoRestaurante= new EmpleadoRestaurante(null, idEmpleado, restauranteBd);
		
		return empleadoRestauranteRepositoryPort.save(empleadoRestaurante);
	}

}
