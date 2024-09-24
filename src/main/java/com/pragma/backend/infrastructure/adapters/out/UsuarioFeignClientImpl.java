package com.pragma.backend.infrastructure.adapters.out;

import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.Usuario;
import com.pragma.backend.domain.ports.out.UsuarioExternalServicePort;

@Component
public class UsuarioFeignClientImpl implements UsuarioExternalServicePort{
	
	private final UsuarioFeignClient usuarioFeignClient;
	
	public UsuarioFeignClientImpl(UsuarioFeignClient usuarioFeignClient) {
		this.usuarioFeignClient = usuarioFeignClient;
	}

	@Override
	public Usuario buscarUsuarioPorId(Long id) {
		return usuarioFeignClient.buscarUsuarioPorId(id);
	}

}
