package com.pragma.backend.domain.ports.in;

import com.pragma.backend.domain.models.ModificarPlato;
import com.pragma.backend.domain.models.Plato;

public interface ModifyPlatoUseCase {

	public Plato modifyPlato(ModificarPlato modificarPlato);
}
