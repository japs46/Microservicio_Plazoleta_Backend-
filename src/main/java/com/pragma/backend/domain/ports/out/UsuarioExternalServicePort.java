package com.pragma.backend.domain.ports.out;

import com.pragma.backend.domain.models.Usuario;

public interface UsuarioExternalServicePort {

	public Usuario buscarUsuarioPorId(Long id); 
}
