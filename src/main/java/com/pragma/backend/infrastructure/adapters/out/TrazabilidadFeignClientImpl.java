package com.pragma.backend.infrastructure.adapters.out;

import org.springframework.stereotype.Component;

import com.pragma.backend.domain.models.LogPedido;
import com.pragma.backend.domain.ports.out.TrazabilidadExternalServicePort;

@Component
public class TrazabilidadFeignClientImpl implements TrazabilidadExternalServicePort{
	
	private final TrazabilidadFeignClient trazabilidadFeignClient;
	
	public TrazabilidadFeignClientImpl(TrazabilidadFeignClient trazabilidadFeignClient) {
		this.trazabilidadFeignClient = trazabilidadFeignClient;
	}

	@Override
	public LogPedido guardarLog(LogPedido logPedido, String token) {
		return trazabilidadFeignClient.guardarLog(logPedido, token);
	}

}
