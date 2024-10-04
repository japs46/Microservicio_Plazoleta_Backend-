package com.pragma.backend.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.backend.application.services.PedidoService;
import com.pragma.backend.domain.models.DetallePedido;
import com.pragma.backend.domain.models.DetallePedidoRequest;
import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.models.RequestPedido;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.models.Rol;
import com.pragma.backend.domain.models.UsuarioLogin;
import com.pragma.backend.infrastructure.adapters.out.UsuarioFeignClient;
import com.pragma.backend.infrastructure.config.SecurityConfig;
import com.pragma.backend.infrastructure.providers.JwtTokenProvider;

@WebMvcTest(PedidoController.class)
@Import(SecurityConfig.class)
public class PedidoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PedidoService pedidoService;
	
	@MockBean
	private JwtTokenProvider jwtTokenProvider;
	
	@MockBean
	private UsuarioFeignClient usuarioFeignClient;
	
	public ObjectMapper objectMapper;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() {
		objectMapper=new ObjectMapper();
		
        when(jwtTokenProvider.validateToken(any(String.class), anyString())).thenReturn(true);
        when(jwtTokenProvider.getUsernameFromToken(any(String.class))).thenReturn("johndoe");
        when(jwtTokenProvider.extractClaim(anyString(), any(Function.class))).thenReturn(2L);

        UsuarioLogin usuarioLogin = new UsuarioLogin("johndoe", Rol.CLIENTE);
        when(usuarioFeignClient.buscarUsuarioPorCorreo("johndoe")).thenReturn(usuarioLogin);
	}
	
	@Test
    void testGuardarPedido_Success() throws Exception {
		
		RequestPedido requestPedido = new RequestPedido(1L, 1L, 1L, List.of(new DetallePedidoRequest(1L, 2)), "PENDIENTE", new Date());
        
		Restaurante restaurante = new Restaurante(
            1L, "Restaurante Prueba", "123456789", "Calle 123", "1234567", 
            "http://logo.com/logo.png", 100L);

        Plato plato = new Plato(
            1L, "Plato de prueba", 20000, "Descripción del plato", "http://imagen.com/plato.png", 
            "Categoría", 1L, true, restaurante);

        DetallePedido detallePedido = new DetallePedido(1L, plato, null, 2);
        List<DetallePedido> detallesPedido = List.of(detallePedido);

        Pedido pedidoBd = new Pedido(1L, 1L, restaurante, detallesPedido, EstadoPedido.PENDIENTE, new Date());

        when(pedidoService.createPedido(any(RequestPedido.class),anyString()))
                .thenReturn(pedidoBd);
        
        String token = "Bearer your-valid-jwt-token";

        mockMvc.perform(post("/api/pedidos/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestPedido))
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }
	
	@Test
    void testGuardarPedido_InternalServerError() throws Exception {
        DetallePedidoRequest detallePedidoRequest = new DetallePedidoRequest(1L, 2);

        RequestPedido requestPedido = new RequestPedido(1L, 1L, 1L, List.of(detallePedidoRequest), "PENDIENTE", new Date());

        when(pedidoService.createPedido(any(RequestPedido.class), anyString()))
                .thenThrow(new RuntimeException("Error al crear pedido"));
        
        String token = "Bearer your-valid-jwt-token";

        mockMvc.perform(post("/api/pedidos/guardar")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestPedido)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$").value("Ocurrio un inconveniente: Error al crear pedido"));
    }

}
