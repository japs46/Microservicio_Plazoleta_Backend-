package com.pragma.backend.infrastructure.adapters.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pragma.backend.domain.models.Usuario;

@FeignClient(name = "Microservicio-Usuarios")
public interface UsuarioFeignClient {

	@GetMapping("/api/usuarios/buscarPorId/{id}")
	public Usuario buscarUsuarioPorId(@PathVariable Long id);
}
