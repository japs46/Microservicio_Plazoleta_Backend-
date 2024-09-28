package com.pragma.backend.application.usecases;

import org.springframework.stereotype.Component;

import com.pragma.backend.domain.exceptions.UserNotOwnerException;
import com.pragma.backend.domain.models.CambioEstadoPlato;
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
	public Plato modifyPlato(ModificarPlato modificarPlato,Long idUser) {
		
		Plato platoBd = platoRepositoryPort.findById(modificarPlato.getId())
				.orElseThrow(()-> new NullPointerException("No se encontro ningun plato con el id: "+modificarPlato.getId()));
		
		if (idUser != platoBd.getRestaurante().getIdUsuarioPropietario()) {
			throw new UserNotOwnerException("El usuario no es el propietario del restaurante.");
		}
		
		Plato platoEditado= new Plato(platoBd.getId(), platoBd.getNombre(), modificarPlato.getPrecio(),
				modificarPlato.getDescripcion(), platoBd.getUrlImagen(), platoBd.getCategoria(),
				null,platoBd.isActivo(), platoBd.getRestaurante());
		
		return platoRepositoryPort.save(platoEditado);
	}

	@Override
	public Plato cambiarEstadoPlato(CambioEstadoPlato cambioEstadoPlato, Long idUser) {
		Plato platoBd = platoRepositoryPort.findById(cambioEstadoPlato.getId())
				.orElseThrow(()-> new NullPointerException("No se encontro ningun plato con el id: "+cambioEstadoPlato.getId()));
		
		if (idUser != platoBd.getRestaurante().getIdUsuarioPropietario()) {
			throw new UserNotOwnerException("El usuario no es el propietario del restaurante.");
		}
		
		if (cambioEstadoPlato.isActivo()==platoBd.isActivo()) {
			throw new RuntimeException(cambioEstadoPlato.isActivo()==true?"Este plato ya se encuenta habilitado":"Este plato ya se encuenta deshabilitado");
		}
		
		Plato platoEditado= new Plato(platoBd.getId(), platoBd.getNombre(), platoBd.getPrecio(),
				platoBd.getDescripcion(), platoBd.getUrlImagen(), platoBd.getCategoria(),
				null, cambioEstadoPlato.isActivo(),platoBd.getRestaurante());
		
		return platoRepositoryPort.save(platoEditado);
	}

}
