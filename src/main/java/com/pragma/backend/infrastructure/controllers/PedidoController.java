package com.pragma.backend.infrastructure.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.backend.application.services.PedidoService;
import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.models.RequestPedido;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	
	private Logger LOGGUER = LoggerFactory.getLogger(PedidoController.class);
	
	private PedidoService pedidoService;
	
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@Operation(summary = "Crear un nuevo pedido", description = "Guarda un nuevo pedido en la base de datos.")
	@ApiResponse(responseCode = "200", description = "pedido guardado exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PostMapping("/guardar")
	public ResponseEntity<?> guardarPedido(@Valid @RequestBody RequestPedido requestPedido, HttpServletRequest request) {

		try {
			LOGGUER.info("Inicio Creacion de Plato");
			String authHeader = request.getHeader("Authorization");
			Pedido pedidoBd = pedidoService.createPedido(requestPedido,authHeader);

			return ResponseEntity.ok(pedidoBd);
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un inconveniente, descripcion del inconveniente: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un inconveniente: " + e.getMessage());
		}

	}
}
