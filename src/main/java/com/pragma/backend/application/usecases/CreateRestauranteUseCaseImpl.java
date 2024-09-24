package com.pragma.backend.application.usecases;

import org.springframework.stereotype.Component;

import com.pragma.backend.domain.exceptions.UserNotOwnerException;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.models.Rol;
import com.pragma.backend.domain.models.Usuario;
import com.pragma.backend.domain.ports.in.CreateRestauranteUseCase;
import com.pragma.backend.domain.ports.out.RestauranteRepositoryPort;
import com.pragma.backend.domain.ports.out.UsuarioExternalServicePort;

@Component
public class CreateRestauranteUseCaseImpl implements CreateRestauranteUseCase{
	
	private final RestauranteRepositoryPort restauranteRepositoryPort;
	
	private final UsuarioExternalServicePort usuarioExternalServicePort;
	
	public CreateRestauranteUseCaseImpl(RestauranteRepositoryPort restauranteRepositoryPort,UsuarioExternalServicePort usuarioExternalServicePort) {
		this.restauranteRepositoryPort = restauranteRepositoryPort;
		this.usuarioExternalServicePort = usuarioExternalServicePort;
	}
	
	

	@Override
	public Restaurante createRestaurante(Restaurante restaurante) {
		
		Usuario usuario = usuarioExternalServicePort.buscarUsuarioPorId(restaurante.getIdUsuarioPropietario());
		System.err.println(usuario.toString());
		if (!usuario.getRol().equals(Rol.PROPIETARIO)) {
			throw new UserNotOwnerException("El usuario no es propietario");
		} 
		
		return restauranteRepositoryPort.save(restaurante);
	}

}
