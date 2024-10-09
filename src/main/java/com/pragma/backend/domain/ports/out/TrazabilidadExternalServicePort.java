package com.pragma.backend.domain.ports.out;

import com.pragma.backend.domain.models.LogPedido;

public interface TrazabilidadExternalServicePort {

	public LogPedido guardarLog(LogPedido logPedido, String token);
}
