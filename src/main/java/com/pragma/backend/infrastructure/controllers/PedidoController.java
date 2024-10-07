package com.pragma.backend.infrastructure.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.backend.application.services.PedidoService;
import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.models.RequestPedido;
import com.pragma.backend.infrastructure.providers.JwtTokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	
	private Logger LOGGUER = LoggerFactory.getLogger(PedidoController.class);
	
	private PedidoService pedidoService;
	
	private final JwtTokenProvider jwtTokenProvider;
	
	public PedidoController(PedidoService pedidoService, JwtTokenProvider jwtTokenProvider) {
		this.pedidoService = pedidoService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Operation(summary = "Crear un nuevo pedido", description = "Guarda un nuevo pedido en la base de datos.")
	@ApiResponse(responseCode = "200", description = "pedido guardado exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PostMapping("/guardar")
	public ResponseEntity<?> guardarPedido(@Valid @RequestBody RequestPedido requestPedido, HttpServletRequest request) {

		try {
			LOGGUER.info("Inicio Creacion de pedido");
			String authHeader = request.getHeader("Authorization");
			Pedido pedidoBd = pedidoService.createPedido(requestPedido,authHeader);

			return ResponseEntity.ok(pedidoBd);
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un inconveniente, descripcion del inconveniente: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un inconveniente: " + e.getMessage());
		}

	}
	
	@Operation(summary = "Crear un nuevo pedido", description = "Guarda un nuevo pedido en la base de datos.")
	@ApiResponse(responseCode = "200", description = "pedido guardado exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@GetMapping("/listar/{estado}")
	public ResponseEntity<?> obtenerPedidos(@PathVariable String estado,@RequestParam(defaultValue = "0") int page, 
	        @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
		try {
			LOGGUER.info("Inicio busqueda pedidos");
			EstadoPedido estadoPedido=EstadoPedido.valueOf(estado);
			String authHeader = request.getHeader("Authorization");
			String token = null;
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
			}
			Long idEmpleado = jwtTokenProvider.extractClaim(token, claims -> claims.get("idUser", Long.class));
			Page<Pedido> listaPedidosPagindos = pedidoService.obtenerTodosPedidos(estadoPedido,idEmpleado,page, size);
			
			return ResponseEntity.ok(listaPedidosPagindos.getContent());
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un inconveniente, descripcion del inconveniente: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un inconveniente: " + e.getMessage());
		}

	}
	
	@Operation(summary = "asignar empleado a pedido", description = "asigna un empleado a un pedido en la base de datos.")
	@ApiResponse(responseCode = "200", description = "empleado asignado a pedido exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PutMapping("/modificar/{id}")
	public ResponseEntity<?> asignarPedido(@PathVariable Long id, HttpServletRequest request) {

		try {
			LOGGUER.info("Iniciando asignacion empleado a pedido");
			String authHeader = request.getHeader("Authorization");
			String token = null;
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
			}
			Long idEmpleado = jwtTokenProvider.extractClaim(token, claims -> claims.get("idUser", Long.class));
			Pedido pedidoModificado = pedidoService.asignarPedido(id, idEmpleado);

			return ResponseEntity.ok(pedidoModificado);
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un inconveniente, descripcion del inconveniente: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un inconveniente: " + e.getMessage());
		}

	}
	
	@Operation(summary = "Notificar pedido listo", description = "Notifica al cliente que su pedido ya esta listo por sms.")
	@ApiResponse(responseCode = "200", description = "Notificaacion enviada exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PutMapping("/pedidoListo/{id}")
	public ResponseEntity<?> pedidoListo(@PathVariable Long id) {

		try {
			LOGGUER.info("Inicio cambio estado pedido listo");
			Pedido pedidoModificado = pedidoService.pedidoListo(id);

			return ResponseEntity.ok(pedidoModificado);
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un inconveniente, descripcion del inconveniente: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un inconveniente: " + e.getMessage());
		}

	}
	
	@Operation(summary = "Notificar pedido listo", description = "Notifica al cliente que su pedido ya esta listo por sms.")
	@ApiResponse(responseCode = "200", description = "Notificaacion enviada exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PutMapping("/pedidoEntregado/{id}")
	public ResponseEntity<?> pedidoEntregado(@PathVariable Long id) {

		try {
			LOGGUER.info("Inicio cambio estado pedido entregado");
			Pedido pedidoModificado = pedidoService.pedidoEntregado(id);

			return ResponseEntity.ok(pedidoModificado);
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un inconveniente, descripcion del inconveniente: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un inconveniente: " + e.getMessage());
		}

	}
}
