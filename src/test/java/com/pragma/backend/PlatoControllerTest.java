package com.pragma.backend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.backend.application.services.PlatoService;
import com.pragma.backend.domain.models.ModificarPlato;
import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.infrastructure.controllers.PlatoController;

@WebMvcTest(PlatoController.class)
public class PlatoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PlatoService platoService;
	
	public ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
			objectMapper=new ObjectMapper();
	}
	
	@Test
    void guardarPlato_ValidPlato_ReturnsOk() throws Exception {
        Restaurante restaurante = new Restaurante(1L, "Restaurante 1", "123456789", "Direccion 123", "1234567890", 
                                                  "http://logo.com", 2L);
        Plato plato = new Plato(1L, "Plato 1", 10000, "Delicioso plato", "http://imagen.com", 
                                "Categoria", true, 2L, restaurante);
        when(platoService.createPlato(any(Plato.class))).thenReturn(plato);

        mockMvc.perform(post("/api/platos/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plato)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Plato 1"))
                .andExpect(jsonPath("$.precio").value(10000))
                .andExpect(jsonPath("$.descripcion").value("Delicioso plato"))
                .andExpect(jsonPath("$.restaurante.nombre").value("Restaurante 1"))
                .andExpect(jsonPath("$.restaurante.nit").value("123456789"))
                .andExpect(jsonPath("$.restaurante.telefono").value("1234567890"));
    }
	
	@Test
    void guardarPlato_InvalidPlato_ReturnsBadRequest() throws Exception {
       
        Plato plato = new Plato(1L, "", -5000, "", "http://imagen.com", 
                                "Categoria", true, 2L, null);

        mockMvc.perform(post("/api/platos/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plato)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validacion Fallida"));
    }
	
	@Test
    void guardarPlato_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/platos/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("No hay cuerpo de solicitud"));
    }
	
	@Test
	void guardarPlato_Exception_ReturnsInternalServerError() throws Exception {
	    Restaurante restaurante = new Restaurante(1L, "Restaurante 1", "123456789", "Direccion 123", "1234567890", 
	                                              "http://logo.com", 2L);
	    Plato plato = new Plato(1L, "Plato 1", 10000, "Delicioso plato", "http://imagen.com", 
	                            "Categoria", true, 2L, restaurante);
	    
	    doThrow(new RuntimeException("Error interno")).when(platoService).createPlato(any());

	    mockMvc.perform(post("/api/platos/guardar")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(plato)))
	            .andExpect(status().isInternalServerError())
	            .andExpect(jsonPath("$").value("Ocurrio un inconveniente: Error interno"));
	}
	
	@Test
    void modificarPlato_ValidData_ReturnsOk() throws Exception {
        ModificarPlato modificarPlato = new ModificarPlato(1L, 15000, "Nueva descripcion");
        Plato platoModificado = new Plato(1L, "Plato Modificado", 15000, "Nueva descripcion", 
                                          "http://imagen.com", "Categoria", true, 2L, null);

        when(platoService.modifyPlato(any(ModificarPlato.class))).thenReturn(platoModificado);

        mockMvc.perform(put("/api/platos/modificar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificarPlato)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descripcion").value("Nueva descripcion"));
    }
	
	@Test
	void modificarPlato_InvalidData_ReturnsBadRequest() throws Exception {
	    ModificarPlato modificarPlato = new ModificarPlato(null, 5000, "descrip"); // Id nulo, precio negativo, descripción vacía

	    mockMvc.perform(put("/api/platos/modificar")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(modificarPlato)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.message").value("Validacion Fallida"))
	            .andExpect(jsonPath("$.errores[0].campo").value("id"))
	            .andExpect(jsonPath("$.errores[0].errorMensaje").value("El ID no puede ser vacio."));
	}
}
