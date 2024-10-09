package com.pragma.backend.infrastructure.adapters.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.pragma.backend.domain.models.LogPedido;

@FeignClient(name = "Microservicio-Trazabilidad")
public interface TrazabilidadFeignClient {

	@GetMapping("/api/logs/guardar")
	public LogPedido guardarLog(@RequestBody LogPedido logPedido, @RequestHeader("Authorization") String token);
	
}
