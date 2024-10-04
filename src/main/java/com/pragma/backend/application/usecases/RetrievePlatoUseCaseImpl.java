package com.pragma.backend.application.usecases;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.ports.in.RetrievePlatoUseCase;
import com.pragma.backend.domain.ports.out.PlatoRepositoryPort;

@Component
public class RetrievePlatoUseCaseImpl implements RetrievePlatoUseCase{
	
	private final PlatoRepositoryPort platoRepositoryPort;
	
	public RetrievePlatoUseCaseImpl(PlatoRepositoryPort platoRepositoryPort) {
		this.platoRepositoryPort = platoRepositoryPort;
	}

	@Override
	public Plato obtenerPlatoPorId(Long id) {
		
		if (id == null) {
	        throw new IllegalArgumentException("El ID no puede ser nulo.");
	    }
	    if (id <= 0) {
	        throw new IllegalArgumentException("El ID debe ser un número positivo.");
	    }
		
		return platoRepositoryPort.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro ningun plato con el id: "+id));
	}

	@Override
	public Page<Plato> obtenerPlatosPorRestaurante(Long restauranteId, String categoria, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Plato> platosPaginados = null;
		
		if (categoria!=null && categoria.length()>0) {
			platosPaginados = platoRepositoryPort.findByRestauranteEntityIdAndCategoria(restauranteId, categoria, pageable);
		}else {
			platosPaginados = platoRepositoryPort.findByRestauranteEntityId(restauranteId, pageable);
		}
		
		return platosPaginados;
	}

}
