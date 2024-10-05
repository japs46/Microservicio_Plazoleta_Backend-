package com.pragma.backend.application.usecases;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.EmpleadoRestaurante;
import com.pragma.backend.domain.ports.in.RetrieveEmpleadoRestauranteUseCase;
import com.pragma.backend.domain.ports.out.EmpleadoRestauranteRepositoryPort;

@Component
public class RetrieveEmpleadoRestauranteUseCaseImpl implements RetrieveEmpleadoRestauranteUseCase{
	
	private final EmpleadoRestauranteRepositoryPort empleadoRestauranteRepositoryPort;
	
	public RetrieveEmpleadoRestauranteUseCaseImpl(EmpleadoRestauranteRepositoryPort empleadoRestauranteRepositoryPort) {
		this.empleadoRestauranteRepositoryPort = empleadoRestauranteRepositoryPort;
	}

	@Override
	public EmpleadoRestaurante obtenerEmpleadoRestaurantePorIdEmpleado(Long idEmpleado) {
		
		if (idEmpleado == null) {
	        throw new IllegalArgumentException("El ID del empleado no puede ser nulo.");
	    }
	    if (idEmpleado <= 0) {
	        throw new IllegalArgumentException("El ID del empleado debe ser un número positivo.");
	    }
		return empleadoRestauranteRepositoryPort.findByIdEmpleado(idEmpleado)
				.orElseThrow(()-> new NoSuchElementException("Este empleado no esta asociado a ningun restaurante con el id: "+idEmpleado));
	}

	
}
