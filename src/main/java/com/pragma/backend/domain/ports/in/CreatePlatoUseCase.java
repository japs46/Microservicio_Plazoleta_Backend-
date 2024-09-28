package com.pragma.backend.domain.ports.in;

import com.pragma.backend.domain.models.Plato;

public interface CreatePlatoUseCase {

	public Plato createPlato(Plato plato,Long idUser); 
}
