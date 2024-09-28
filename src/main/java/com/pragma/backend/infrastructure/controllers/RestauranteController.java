package com.pragma.backend.infrastructure.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.backend.application.services.RestauranteService;
import com.pragma.backend.domain.exceptions.UserNotOwnerException;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.models.RestauranteInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

	private Logger LOGGUER = LoggerFactory.getLogger(RestauranteController.class);

	private RestauranteService restauranteService;

	public RestauranteController(RestauranteService restauranteService) {
		this.restauranteService = restauranteService;
	}

	@Operation(summary = "Crear un nuevo Restaurante", description = "Guarda un nuevo Restaurante en la base de datos.")
	@ApiResponse(responseCode = "200", description = "Restaurante guardado exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PostMapping("/guardar")
	public ResponseEntity<?> guardarRestaurante(@Valid @RequestBody Restaurante restaurante,
			HttpServletRequest request) {

		try {
			LOGGUER.info("Inicio Creacion de Restaurante");
			String authHeader = request.getHeader("Authorization");
			Restaurante restauranteBd = restauranteService.createRestaurante(restaurante,authHeader);

			return ResponseEntity.ok(restauranteBd);
		} catch (UserNotOwnerException e) {
			LOGGUER.error("Ocurrio un problema: " + e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un error, descripcion del error: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un error en el servidor");
		}

	}
	
	@GetMapping("/buscarPorIdPropietario/{idPropietario}")
	public ResponseEntity<?> buscarRestaurantePorPropietario(@PathVariable Long idPropietario){
		try {
			LOGGUER.info("Inicio busqueda restaurante por id del propetario");
			
			Restaurante restauranteBd = restauranteService.obtenerRestaurantePorIdPropietario(idPropietario);
					
			return ResponseEntity.ok(restauranteBd);
			
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un error, descripcion del error: " + e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
		
	}
	
	@GetMapping("/buscarTodos")
	public ResponseEntity<?> buscarTodosLosRestaurantes(@RequestParam(defaultValue = "0") int page, 
	        @RequestParam(defaultValue = "10") int size){
		try {
			LOGGUER.info("Inicio busqueda restaurantes");
			
			Page<RestauranteInfo> listaRestaurantesPaginada = restauranteService.obtenerTodosLosRestaurantes(page, size);
					
			return ResponseEntity.ok(listaRestaurantesPaginada.getContent());
			
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un error, descripcion del error: " + e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
		
	}
}
