package com.pragma.backend.application.usecases;

import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.ModificarPlato;
import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.ports.in.ModifyPlatoUseCase;
import com.pragma.backend.domain.ports.out.PlatoRepositoryPort;

@Component
public class ModifyPlatoUseCaseImpl implements ModifyPlatoUseCase{
	
	private final PlatoRepositoryPort platoRepositoryPort;
	
	public ModifyPlatoUseCaseImpl(PlatoRepositoryPort platoRepositoryPort) {
		this.platoRepositoryPort = platoRepositoryPort;
	}

	@Override
	public Plato modifyPlato(ModificarPlato modificarPlato) {
		
		Plato platoBd = platoRepositoryPort.findById(modificarPlato.getId())
				.orElseThrow(()-> new NullPointerException("No se encontro ningun plato con el id: "+modificarPlato.getId()));
		
		Plato platoEditado= new Plato(platoBd.getId(), platoBd.getNombre(), modificarPlato.getPrecio(),
				modificarPlato.getDescripcion(), platoBd.getUrlImagen(), platoBd.getCategoria(), platoBd.isActivo(),
				platoBd.getIdUsuarioPropietario(), platoBd.getRestaurante());
		
		return platoRepositoryPort.save(platoEditado);
	}

}
