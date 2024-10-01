package com.pragma.backend.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pragma.backend.domain.exceptions.UserNotOwnerException;
import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.ports.in.RetrieveRestauranteUseCase;
import com.pragma.backend.domain.ports.out.PlatoRepositoryPort;

@SpringBootTest(properties = {
	    "eureka.client.enabled=false"
	})
public class CreatePlatoUseCaseImplTest {

	@MockBean
    private PlatoRepositoryPort platoRepositoryPort;
	
	@MockBean
	private @Qualifier("retrieveRestauranteUseCaseImpl") RetrieveRestauranteUseCase retrieveRestauranteUseCase;

    @Autowired
    private CreatePlatoUseCaseImpl createPlatoUseCase;

    private Plato plato;
    private Restaurante restaurante;
    
    @BeforeEach
    void setUp() {
        restaurante = new Restaurante(1L, "Restaurante Ejemplo", "123456789", "Calle 123", "987654321", "http://logo.com", 100L);
        plato = new Plato(1L, "Plato Ejemplo", 500, "Descripción", "http://imagen.com", "Categoria", 1L, true, restaurante);
    }
    
    @Test
    void testCreatePlato_Success() {
        when(retrieveRestauranteUseCase.obtenerRestaurantePorId(anyLong())).thenReturn(restaurante);
        when(platoRepositoryPort.save(any(Plato.class))).thenReturn(plato);

        Plato result = createPlatoUseCase.createPlato(plato, 100L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Plato Ejemplo", result.getNombre());
        assertNotNull(result.getRestaurante());
        assertEquals("Restaurante Ejemplo", result.getRestaurante().getNombre());
        verify(platoRepositoryPort).save(any(Plato.class));
    }

    @Test
    void testCreatePlato_UserNotOwnerException() {
        when(retrieveRestauranteUseCase.obtenerRestaurantePorId(anyLong())).thenReturn(restaurante);

        assertThrows(UserNotOwnerException.class, () -> {
            createPlatoUseCase.createPlato(plato, 101L);
        });

        verify(platoRepositoryPort, never()).save(any(Plato.class));
    }

    @Test
    void testCreatePlato_RetrieveRestauranteUseCaseCalled() {
        when(retrieveRestauranteUseCase.obtenerRestaurantePorId(anyLong())).thenReturn(restaurante);

        createPlatoUseCase.createPlato(plato, 100L);

        verify(retrieveRestauranteUseCase, times(1)).obtenerRestaurantePorId(anyLong());
    }
}
