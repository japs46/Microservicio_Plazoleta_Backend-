package com.pragma.backend.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.models.RestauranteInfo;
import com.pragma.backend.domain.ports.out.RestauranteRepositoryPort;

@SpringBootTest(properties = {
	    "eureka.client.enabled=false"
	})
public class RetrieveRestauranteUseCaseImplTest {

	@MockBean
	private RestauranteRepositoryPort restauranteRepositoryPort;
	
	@Autowired
	private RetrieveRestauranteUseCaseImpl retrieveRestauranteUseCase;
	
	private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante(1L, "Restaurante Prueba", "123456789", "Calle 123", "987654321", "http://logo.com", 100L);
    }

    @Nested
    class ObtenerRestaurantePorIdPropietarioTests {

        @Test
        void testObtenerRestaurantePorIdPropietario_ValidId() {
            when(restauranteRepositoryPort.findByIdPropietario(100L)).thenReturn(Optional.of(restaurante));

            Restaurante result = retrieveRestauranteUseCase.obtenerRestaurantePorIdPropietario(100L);

            assertNotNull(result);
            assertEquals("Restaurante Prueba", result.getNombre());

            verify(restauranteRepositoryPort, times(1)).findByIdPropietario(100L);
        }

        @Test
        void testObtenerRestaurantePorIdPropietario_NullId() {
            assertThrows(IllegalArgumentException.class, () -> {
                retrieveRestauranteUseCase.obtenerRestaurantePorIdPropietario(null);
            });

            verify(restauranteRepositoryPort, times(0)).findByIdPropietario(anyLong());
        }

        @Test
        void testObtenerRestaurantePorIdPropietario_NegativeId() {
            assertThrows(IllegalArgumentException.class, () -> {
                retrieveRestauranteUseCase.obtenerRestaurantePorIdPropietario(-1L);
            });

            verify(restauranteRepositoryPort, times(0)).findByIdPropietario(anyLong());
        }
    }

    @Nested
    class ObtenerRestaurantePorIdTests {

        @Test
        void testObtenerRestaurantePorId_ValidId() {
            when(restauranteRepositoryPort.findById(1L)).thenReturn(Optional.of(restaurante));

            Restaurante result = retrieveRestauranteUseCase.obtenerRestaurantePorId(1L);

            assertNotNull(result);
            assertEquals("Restaurante Prueba", result.getNombre());

            verify(restauranteRepositoryPort, times(1)).findById(1L);
        }

        @Test
        void testObtenerRestaurantePorId_NullId() {
            assertThrows(IllegalArgumentException.class, () -> {
                retrieveRestauranteUseCase.obtenerRestaurantePorId(null);
            });

            verify(restauranteRepositoryPort, times(0)).findById(anyLong());
        }

        @Test
        void testObtenerRestaurantePorId_NegativeId() {
            assertThrows(IllegalArgumentException.class, () -> {
                retrieveRestauranteUseCase.obtenerRestaurantePorId(-1L);
            });

            verify(restauranteRepositoryPort, times(0)).findById(anyLong());
        }
    }

    @Nested
    class ObtenerTodosLosRestaurantesTests {

        @Test
        void testObtenerTodosLosRestaurantes() {
            Pageable pageable = PageRequest.of(0, 5);
            Page<RestauranteInfo> page = new PageImpl<>(List.of(new RestauranteInfo("Restaurante 1", "http:localhost:4336")));

            when(restauranteRepositoryPort.findAll(pageable)).thenReturn(page);

            Page<RestauranteInfo> result = retrieveRestauranteUseCase.obtenerTodosLosRestaurantes(0, 5);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());

            verify(restauranteRepositoryPort, times(1)).findAll(pageable);
        }
    }
}
