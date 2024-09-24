package com.pragma.backend.infrastructure.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.backend.application.services.RestauranteService;
import com.pragma.backend.domain.models.Restaurante;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

	private Logger LOGGUER = LoggerFactory.getLogger(RestauranteController.class);

	private RestauranteService restauranteService;

	public RestauranteController(RestauranteService restauranteService) {
		this.restauranteService = restauranteService;
	}

	@Operation(summary = "Crear un nuevo Propietario", description = "Guarda un nuevo propietario en la base de datos.")
	@ApiResponse(responseCode = "200", description = "Propietario guardada exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PostMapping("/guardar")
	public ResponseEntity<?> guardarPropietario(@Valid @RequestBody Restaurante restaurante) {

		try {
			LOGGUER.info("Inicio Creacion de Restaurante");
			Restaurante propietarioBd = restauranteService.createRestaurante(restaurante);

			return ResponseEntity.ok(propietarioBd);
//		} catch (UnderageException e) {
//			LOGGUER.error("Ocurrio un problema: " + e.getMessage());
//			return ResponseEntity.internalServerError().body(e.getMessage());
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un error, descripcion del error: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un error en el servidor");
		}

	}
}
