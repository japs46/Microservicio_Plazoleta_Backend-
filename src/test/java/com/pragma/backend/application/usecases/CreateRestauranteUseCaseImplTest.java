package com.pragma.backend.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pragma.backend.domain.exceptions.UserNotOwnerException;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.models.Rol;
import com.pragma.backend.domain.models.Usuario;
import com.pragma.backend.domain.ports.out.RestauranteRepositoryPort;
import com.pragma.backend.domain.ports.out.UsuarioExternalServicePort;

@SpringBootTest(properties = {
	    "eureka.client.enabled=false"
	})
public class CreateRestauranteUseCaseImplTest {

	@MockBean
	private RestauranteRepositoryPort restauranteRepositoryPort;
	
	@MockBean
	private UsuarioExternalServicePort usuarioExternalServicePort;
	
	@Autowired
	private CreateRestauranteUseCaseImpl createRestauranteUseCase;
	
	private Restaurante restaurante;
    private Usuario propietario;
    private Usuario noPropietario;
    
    private final String token = "Bearer valid-token";
    
    @BeforeEach
    void setUp() {
        restaurante = new Restaurante(1L, "Restaurante Ejemplo", "123456789", 
                                      "Calle 123", "987654321", "http://logo.com", 100L);

        propietario = new Usuario(100L,"Juan","Pérez","123456789","3001234567",new Date(),"juan@mail.com",
        		"claveEncriptada123",Rol.PROPIETARIO);
        
        noPropietario = new Usuario(101L, "Carlos","Rosal","12345","3102134567", new Date(),"carlos@mail.com",
        		"Encriptada123",Rol.CLIENTE);
    }
    
    @Test
    void testCreateRestaurante_Success() {
        when(usuarioExternalServicePort.buscarUsuarioPorId(100L, token)).thenReturn(propietario);
        
        when(restauranteRepositoryPort.save(any(Restaurante.class))).thenReturn(restaurante);

        Restaurante resultado = createRestauranteUseCase.createRestaurante(restaurante, token);
        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Restaurante Ejemplo", resultado.getNombre());
        assertEquals("http://logo.com", resultado.getUrlLogo());
        assertEquals("Calle 123", resultado.getDireccion());
        assertEquals(100L, resultado.getIdUsuarioPropietario());
        

        verify(restauranteRepositoryPort).save(restaurante);
    }

    @Test
    void testCreateRestaurante_UserNotOwnerException() {
        when(usuarioExternalServicePort.buscarUsuarioPorId(100L, token)).thenReturn(noPropietario);

        assertThrows(UserNotOwnerException.class, () -> {
            createRestauranteUseCase.createRestaurante(restaurante, token);
        });

        verify(restauranteRepositoryPort, org.mockito.Mockito.never()).save(any(Restaurante.class));
    }
}
