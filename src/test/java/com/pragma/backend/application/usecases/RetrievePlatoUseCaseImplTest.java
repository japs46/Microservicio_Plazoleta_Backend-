package com.pragma.backend.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.ports.out.PlatoRepositoryPort;

@SpringBootTest(properties = {
	    "eureka.client.enabled=false"
	})
public class RetrievePlatoUseCaseImplTest {

	@MockBean
    private PlatoRepositoryPort platoRepositoryPort;
	
	@Autowired
	private RetrievePlatoUseCaseImpl retrievePlatoUseCase;
	
	private Plato plato;

    @BeforeEach
    void setUp() {
        plato = new Plato(1L, "Plato de prueba", 10000, "Descripción", "urlImagen", "Categoria", null, true, null);
    }
    
    @Test
    void testObtenerPlatoPorId() {
        when(platoRepositoryPort.findById(1L)).thenReturn(Optional.of(plato));

        Plato result = retrievePlatoUseCase.obtenerPlatoPorId(1L);

        assertNotNull(result);
        assertEquals("Plato de prueba", result.getNombre());

        verify(platoRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void testObtenerPlatoPorId_NotFound() {
        when(platoRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            retrievePlatoUseCase.obtenerPlatoPorId(1L);
        });

        verify(platoRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void testObtenerPlatosPorRestauranteConCategoria() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Plato> page = new PageImpl<>(List.of(plato));
        when(platoRepositoryPort.findByRestauranteEntityIdAndCategoria(1L, "Categoria", pageable)).thenReturn(page);

        Page<Plato> result = retrievePlatoUseCase.obtenerPlatosPorRestaurante(1L, "Categoria", 0, 5);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        verify(platoRepositoryPort, times(1)).findByRestauranteEntityIdAndCategoria(1L, "Categoria", pageable);
    }

    @Test
    void testObtenerPlatosPorRestauranteSinCategoria() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Plato> page = new PageImpl<>(List.of(plato));
        when(platoRepositoryPort.findByRestauranteEntityId(1L, pageable)).thenReturn(page);

        Page<Plato> result = retrievePlatoUseCase.obtenerPlatosPorRestaurante(1L, null, 0, 5);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        verify(platoRepositoryPort, times(1)).findByRestauranteEntityId(1L, pageable);
    }
}
