package com.pragma.backend.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pragma.backend.domain.exceptions.UserNotOwnerException;
import com.pragma.backend.domain.models.CambioEstadoPlato;
import com.pragma.backend.domain.models.ModificarPlato;
import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.ports.out.PlatoRepositoryPort;

@SpringBootTest(properties = {
	    "eureka.client.enabled=false"
	})
public class ModifyPlatoUseCaseImplTest {

	@MockBean
    private PlatoRepositoryPort platoRepositoryPort;
	
	@Autowired
    private ModifyPlatoUseCaseImpl modifyPlatoUseCase;
	
	private Plato plato;
	private Plato platoModificado;
    private Restaurante restaurante;
    private ModificarPlato modificarPlato;
    private CambioEstadoPlato cambioEstadoPlato;
    
    @BeforeEach
    void setUp() {
        restaurante = new Restaurante(1L, "Restaurante Ejemplo", "123456789", "Calle 123", "987654321", "http://logo.com", 100L);
        plato = new Plato(1L, "Plato Ejemplo", 500, "Descripción", "http://imagen.com", "Categoria", 1L, true, restaurante);

        modificarPlato = new ModificarPlato(1L, 600, "Nueva Descripción");
        cambioEstadoPlato = new CambioEstadoPlato(1L, false);
    }
    
    @Nested
    class ModifyPlatoTests {

        @Test
        void testModifyPlato_Success() {
        	 platoModificado = new Plato(1L, "Plato Ejemplo", 600, "Nueva Descripción", "http://imagen.com", "Categoria", 1L, true, restaurante);
        	
            when(platoRepositoryPort.findById(anyLong())).thenReturn(Optional.of(plato));
            when(platoRepositoryPort.save(any(Plato.class))).thenReturn(platoModificado);

            Plato result = modifyPlatoUseCase.modifyPlato(modificarPlato, 100L);

            assertNotNull(result);
            assertEquals(1L, result.getId());
            assertEquals("Plato Ejemplo", result.getNombre());
            assertThat(plato.getPrecio()!=result.getPrecio());
            assertThat(!plato.getDescripcion().equals(result.getPrecio()));
            assertEquals("Nueva Descripción", result.getDescripcion());
            assertEquals(600, result.getPrecio());
            assertNotNull(result.getRestaurante());
            assertEquals("Restaurante Ejemplo", result.getRestaurante().getNombre());
            verify(platoRepositoryPort).save(any(Plato.class));
        }

        @Test
        void testModifyPlato_UserNotOwnerException() {
            when(platoRepositoryPort.findById(anyLong())).thenReturn(Optional.of(plato));

            assertThrows(UserNotOwnerException.class, () -> {
                modifyPlatoUseCase.modifyPlato(modificarPlato, 101L);
            });

            verify(platoRepositoryPort, never()).save(any(Plato.class));
        }

        @Test
        void testModifyPlato_PlateNotFound() {
            when(platoRepositoryPort.findById(anyLong())).thenReturn(Optional.empty());

            assertThrows(NullPointerException.class, () -> {
                modifyPlatoUseCase.modifyPlato(modificarPlato, 100L);
            });
        }
    }

    @Nested
    class CambiarEstadoPlatoTests {

        @Test
        void testCambiarEstadoPlato_Success() {
        	
        	platoModificado = new Plato(1L, "Plato Ejemplo", 500, "Descripción", "http://imagen.com", "Categoria", 1L, false, restaurante);
        	
            when(platoRepositoryPort.findById(anyLong())).thenReturn(Optional.of(plato));
            when(platoRepositoryPort.save(any(Plato.class))).thenReturn(plato);

            Plato result = modifyPlatoUseCase.cambiarEstadoPlato(cambioEstadoPlato, 100L);

            assertNotNull(result);
            assertThat(plato.isActivo()!=platoModificado.isActivo());
            assertEquals(false, platoModificado.isActivo());
            verify(platoRepositoryPort).save(any(Plato.class));
        }

        @Test
        void testCambiarEstadoPlato_AlreadyActive() {
            Plato platoActivo = new Plato(1L, "Plato Ejemplo", 500, "Descripción",
                    "http://imagen.com", "Categoria", 1L, true, restaurante);

            cambioEstadoPlato = new CambioEstadoPlato(1L, true);

            when(platoRepositoryPort.findById(anyLong())).thenReturn(Optional.of(platoActivo));

            assertThrows(RuntimeException.class, () -> {
                modifyPlatoUseCase.cambiarEstadoPlato(cambioEstadoPlato, 100L);
            });

            verify(platoRepositoryPort, never()).save(any(Plato.class));
        }

        @Test
        void testCambiarEstadoPlato_UserNotOwnerException() {
            when(platoRepositoryPort.findById(anyLong())).thenReturn(Optional.of(plato));

            assertThrows(UserNotOwnerException.class, () -> {
                modifyPlatoUseCase.cambiarEstadoPlato(cambioEstadoPlato, 101L);
            });

            verify(platoRepositoryPort, never()).save(any(Plato.class));
        }

        @Test
        void testCambiarEstadoPlato_PlateNotFound() {
            when(platoRepositoryPort.findById(anyLong())).thenReturn(Optional.empty());

            assertThrows(NullPointerException.class, () -> {
                modifyPlatoUseCase.cambiarEstadoPlato(cambioEstadoPlato, 100L);
            });
        }
    }
}
