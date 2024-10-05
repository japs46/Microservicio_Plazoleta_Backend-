package com.pragma.backend.infrastructure.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.backend.application.services.EmpleadoService;
import com.pragma.backend.domain.models.EmpleadoRestaurante;
import com.pragma.backend.infrastructure.providers.JwtTokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/empleadosRestaurante")
public class EmpleadoRestauranteController {
	
	private Logger LOGGUER = LoggerFactory.getLogger(EmpleadoRestauranteController.class);
	
	private final EmpleadoService empleadoService;
	
	private final JwtTokenProvider jwtTokenProvider;
	
	public EmpleadoRestauranteController(EmpleadoService empleadoService,JwtTokenProvider jwtTokenProvider) {
		this.empleadoService = empleadoService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Operation(summary = "Crear una asociacion empleado restaurante", description = "Guarda una nueva asociacion empleado restaurante en la base de datos.")
	@ApiResponse(responseCode = "200", description = "asociacion empleado restaurante guardada exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PostMapping("/guardar/{idEmpleado}")
	public ResponseEntity<?> guardarEmpleadoRestaurante(@PathVariable Long idEmpleado, HttpServletRequest request) {

		try {
			LOGGUER.info("Inicio asignacion empleado a restaurante");
			String authHeader = request.getHeader("Authorization");
			String token = null;
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
			}
			Long idUser = jwtTokenProvider.extractClaim(token, claims -> claims.get("idUser", Long.class));
			EmpleadoRestaurante empleadoRestauranteBd = empleadoService.createEmpleadoRestaurante(idEmpleado,idUser);

			return ResponseEntity.ok(empleadoRestauranteBd);
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un inconveniente, descripcion del inconveniente: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un inconveniente: " + e.getMessage());
		}

	}
}
