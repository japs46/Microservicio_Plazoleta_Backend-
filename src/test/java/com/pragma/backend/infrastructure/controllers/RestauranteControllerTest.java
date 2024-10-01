package com.pragma.backend.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.backend.application.services.RestauranteService;
import com.pragma.backend.domain.exceptions.UserNotOwnerException;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.models.RestauranteInfo;
import com.pragma.backend.domain.models.Rol;
import com.pragma.backend.domain.models.UsuarioLogin;
import com.pragma.backend.infrastructure.adapters.out.UsuarioFeignClient;
import com.pragma.backend.infrastructure.config.SecurityConfig;
import com.pragma.backend.infrastructure.providers.JwtTokenProvider;

@WebMvcTest(RestauranteController.class)
@Import(SecurityConfig.class)
public class RestauranteControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RestauranteService restauranteService;
	
	@MockBean
	private JwtTokenProvider jwtTokenProvider;
	
	@MockBean
	private UsuarioFeignClient usuarioFeignClient;
	
	public ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		objectMapper=new ObjectMapper();
		when(jwtTokenProvider.validateToken(any(String.class), anyString())).thenReturn(true);
	    when(jwtTokenProvider.getUsernameFromToken(any(String.class))).thenReturn("johndoe");
	    
	    UsuarioLogin usuarioLogin = new UsuarioLogin("johndoe", Rol.ADMIN);
        when(usuarioFeignClient.buscarUsuarioPorCorreo("johndoe")).thenReturn(usuarioLogin);
	}
	
	@Nested
	class GuardarRestauranteTests {
		
		@Test
	    public void testGuardarRestaurante_Success() throws Exception {
		 
	        Restaurante restaurante = new Restaurante( 1L, "Restaurante Ejemplo", "123456789", "Calle 123", 
	                "987654321", "http://logo.com/logo.png", 100L);

	        when(restauranteService.createRestaurante(any(Restaurante.class),anyString())).thenReturn(restaurante);
	        
	        String token = "Bearer your-valid-jwt-token";

	        mockMvc.perform(post("/api/restaurantes/guardar")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(restaurante))
	                .header("Authorization", token))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.id").value(1L))
	                .andExpect(jsonPath("$.nombre").value("Restaurante Ejemplo"))
	                .andExpect(jsonPath("$.nit").value("123456789"))
	                .andExpect(jsonPath("$.direccion").value("Calle 123"))
	                .andExpect(jsonPath("$.telefono").value("987654321"))
	                .andExpect(jsonPath("$.urlLogo").value("http://logo.com/logo.png"))
	                .andExpect(jsonPath("$.idUsuarioPropietario").value(100L));
	    }

		@Test
	    public void testGuardarRestaurante_UserNotOwnerException() throws Exception {
		 
	        Restaurante restaurante = new Restaurante(1L, "Restaurante Ejemplo", "123456789", "Calle 123", 
	                "987654321", "http://logo.com/logo.png", 100L);

	        when(restauranteService.createRestaurante(any(Restaurante.class),anyString()))
	                .thenThrow(new UserNotOwnerException("El usuario no es propietario"));
	        
	        String token = "Bearer your-valid-jwt-token";

	        mockMvc.perform(post("/api/restaurantes/guardar")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(restaurante))
	                .header("Authorization", token))
	                .andExpect(status().isInternalServerError())
	                .andExpect(jsonPath("$").value("El usuario no es propietario"));
	    }

		@Test
	    public void testGuardarRestaurante_ServerException() throws Exception {

		 Restaurante restaurante = new Restaurante(
	                1L, "Restaurante Ejemplo",  "123456789",  "Calle 123", 
	                "987654321",  "http://logo.com/logo.png", 100L);

	        when(restauranteService.createRestaurante(any(Restaurante.class),anyString()))
	                .thenThrow(new RuntimeException("Error del servidor"));
	        
	        String token = "Bearer your-valid-jwt-token";

	        mockMvc.perform(post("/api/restaurantes/guardar")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(restaurante))
	                .header("Authorization", token))
	                .andExpect(status().isInternalServerError())
	                .andExpect(jsonPath("$").value("Ocurrio un error en el servidor"));
	    }
	}

	@Nested
	class BuscarRestaurantePorPropietarioTests {

	    @Test
	    void validId_ReturnsOk() throws Exception {
	        Long idPropietario = 1L;
	        Restaurante restaurante = new Restaurante(1L, "Restaurante 1", "123456789", "Direccion 123", "1234567890", "http://logo.com", 2L);
	        
	        when(restauranteService.obtenerRestaurantePorIdPropietario(idPropietario)).thenReturn(restaurante);
	        
	        String token = "Bearer your-valid-jwt-token";
	        
	        mockMvc.perform(get("/api/restaurantes/buscarPorIdPropietario/{idPropietario}", idPropietario)
	                .header("Authorization", token))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.nombre").value("Restaurante 1"))
	                .andExpect(jsonPath("$.telefono").value("1234567890"));
	    }

	    @Test
	    void nonExistentId_ReturnsInternalServerError() throws Exception {
	        Long idPropietario = 1L;
	        
	        when(restauranteService.obtenerRestaurantePorIdPropietario(idPropietario)).thenThrow(new RuntimeException("Error interno"));

	        String token = "Bearer your-valid-jwt-token";
	        
	        mockMvc.perform(get("/api/restaurantes/buscarPorIdPropietario/{idPropietario}", idPropietario)
	                .header("Authorization", token))
	                .andExpect(status().isInternalServerError())
	                .andExpect(jsonPath("$").value("Error interno"));
	    }
	}
	
	@Nested
	class BuscarTodosLosRestaurantesTests {

	    @Test
	    void validRequest_ReturnsOk() throws Exception {
	    	
	    	UsuarioLogin usuarioLogin = new UsuarioLogin("johndoe", Rol.CLIENTE);
	        when(usuarioFeignClient.buscarUsuarioPorCorreo("johndoe")).thenReturn(usuarioLogin);
	    	
	        int page = 0;
	        int size = 10;
	        List<RestauranteInfo> listaRestaurantes = Arrays.asList(
	            new RestauranteInfo("Restaurante 1", "123456789"),
	            new RestauranteInfo("Restaurante 2", "987654321")
	        );
	        Page<RestauranteInfo> pageResponse = new PageImpl<>(listaRestaurantes);

	        when(restauranteService.obtenerTodosLosRestaurantes(page, size)).thenReturn(pageResponse);

	        mockMvc.perform(get("/api/restaurantes/buscarTodos")
	                .param("page", String.valueOf(page))
	                .param("size", String.valueOf(size))
	                .header("Authorization", "Bearer your-valid-jwt-token"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$[0].nombre").value("Restaurante 1"))
	                .andExpect(jsonPath("$[1].nombre").value("Restaurante 2"));
	    }

	    @Test
	    void internalServerError_ReturnsErrorMessage() throws Exception {
	    	
	    	UsuarioLogin usuarioLogin = new UsuarioLogin("johndoe", Rol.CLIENTE);
	        when(usuarioFeignClient.buscarUsuarioPorCorreo("johndoe")).thenReturn(usuarioLogin);
	    	
	        int page = 0;
	        int size = 10;

	        when(restauranteService.obtenerTodosLosRestaurantes(page, size)).thenThrow(new RuntimeException("Error interno"));

	        mockMvc.perform(get("/api/restaurantes/buscarTodos")
	                .param("page", String.valueOf(page))
	                .param("size", String.valueOf(size))
	                .header("Authorization", "Bearer your-valid-jwt-token"))
	                .andExpect(status().isInternalServerError())
	                .andExpect(jsonPath("$").value("Error interno"));
	    }
	}
}
