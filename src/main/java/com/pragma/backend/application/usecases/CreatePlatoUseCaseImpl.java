package com.pragma.backend.application.usecases;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pragma.backend.domain.exceptions.UserNotOwnerException;
import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.ports.in.CreatePlatoUseCase;
import com.pragma.backend.domain.ports.in.RetrieveRestauranteUseCase;
import com.pragma.backend.domain.ports.out.PlatoRepositoryPort;

@Component
public class CreatePlatoUseCaseImpl implements CreatePlatoUseCase{
	
	private final PlatoRepositoryPort platoRepositoryPort;
	
	private final RetrieveRestauranteUseCase retrieveRestauranteUseCase;
	
	public CreatePlatoUseCaseImpl(PlatoRepositoryPort platoRepositoryPort,
			@Qualifier("retrieveRestauranteUseCaseImpl") RetrieveRestauranteUseCase retrieveRestauranteUseCase) {
		this.platoRepositoryPort = platoRepositoryPort;
		this.retrieveRestauranteUseCase = retrieveRestauranteUseCase;
	}

	@Override
	public Plato createPlato(Plato plato, Long idUser) {
		
		Restaurante restaurante = retrieveRestauranteUseCase.obtenerRestaurantePorId(plato.getIdRestaurante());
		
		if (idUser != restaurante.getIdUsuarioPropietario()) {
			throw new UserNotOwnerException("El usuario no es el propietario del restaurante.");
		}
		
		Plato platoRestaurante = new Plato(plato.getId(), plato.getNombre(), plato.getPrecio(),
				plato.getDescripcion(), plato.getUrlImagen(), plato.getCategoria(), null, restaurante);
		
		return platoRepositoryPort.save(platoRestaurante);
	}

}
