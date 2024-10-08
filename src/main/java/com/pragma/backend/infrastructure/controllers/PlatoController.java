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

import com.pragma.backend.application.services.PlatoService;
import com.pragma.backend.domain.models.CambioEstadoPlato;
import com.pragma.backend.domain.models.ModificarPlato;
import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.infrastructure.providers.JwtTokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

	private Logger LOGGUER = LoggerFactory.getLogger(PlatoController.class);

	private PlatoService platoService;
	
	private final JwtTokenProvider jwtTokenProvider;

	public PlatoController(PlatoService platoService, JwtTokenProvider jwtTokenProvider) {
		this.platoService = platoService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Operation(summary = "Crear un nuevo Plato", description = "Guarda un nuevo Plato en la base de datos.")
	@ApiResponse(responseCode = "200", description = "Plato guardado exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PostMapping("/guardar")
	public ResponseEntity<?> guardarPlato(@Valid @RequestBody Plato plato, HttpServletRequest request) {

		try {
			LOGGUER.info("Inicio Creacion de Plato");
			String authHeader = request.getHeader("Authorization");
			String token = null;
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
			}
			Long idUser = jwtTokenProvider.extractClaim(token, claims -> claims.get("idUser", Long.class));
			Plato platoBd = platoService.createPlato(plato,idUser);

			return ResponseEntity.ok(platoBd);
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un inconveniente, descripcion del inconveniente: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un inconveniente: " + e.getMessage());
		}

	}

	@Operation(summary = "Modificar un Plato", description = "Modifica un Plato en la base de datos.")
	@ApiResponse(responseCode = "200", description = "Plato modificado exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PutMapping("/modificar")
	public ResponseEntity<?> modificarPlato(@Valid @RequestBody ModificarPlato modificarPlato,HttpServletRequest request) {

		try {
			LOGGUER.info("Inicio Modificacion de Plato");
			String authHeader = request.getHeader("Authorization");
			String token = null;
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
			}
			Long idUser = jwtTokenProvider.extractClaim(token, claims -> claims.get("idUser", Long.class));
			Plato platoBd = platoService.modifyPlato(modificarPlato,idUser);

			return ResponseEntity.ok(platoBd);
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un inconveniente, descripcion del inconveniente: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un inconveniente: " + e.getMessage());
		}

	}
	
	@Operation(summary = "Modificar un Plato", description = "Modifica un Plato en la base de datos.")
	@ApiResponse(responseCode = "200", description = "Plato modificado exitosamente")
	@ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PutMapping("/cambiarEstado")
	public ResponseEntity<?> cambioEstado(@RequestBody CambioEstadoPlato cambioEstadoPlato,HttpServletRequest request) {

		try {
			LOGGUER.info("Inicio Modificacion de Plato");
			String authHeader = request.getHeader("Authorization");
			String token = null;
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
			}
			Long idUser = jwtTokenProvider.extractClaim(token, claims -> claims.get("idUser", Long.class));
			Plato platoBd = platoService.cambiarEstadoPlato(cambioEstadoPlato,idUser);

			return ResponseEntity.ok(platoBd);
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un inconveniente, descripcion del inconveniente: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un inconveniente: " + e.getMessage());
		}

	}
	
	@GetMapping("/porRestaurante/{idRestaurante}")
    public ResponseEntity<?> listarPlatosPorRestaurante(
            @PathVariable Long idRestaurante,
            @RequestParam(required = false) String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
		try {
			LOGGUER.info("Inicio Modificacion de Plato");
	        Page<Plato> listaPlatosPaginada = platoService.obtenerPlatosPorRestaurante(idRestaurante, categoria, page,size);
	        return ResponseEntity.ok(listaPlatosPaginada.getContent());
		} catch (Exception e) {
			LOGGUER.error("Ocurrio un inconveniente, descripcion del inconveniente: " + e.getMessage());
			return ResponseEntity.internalServerError().body("Ocurrio un inconveniente: " + e.getMessage());
		} 
			
    }
}
