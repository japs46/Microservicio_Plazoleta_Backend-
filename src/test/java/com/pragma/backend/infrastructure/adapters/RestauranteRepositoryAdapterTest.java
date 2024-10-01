package com.pragma.backend.infrastructure.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.data.domain.Pageable;

import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.models.RestauranteInfo;
import com.pragma.backend.infrastructure.entities.RestauranteEntity;
import com.pragma.backend.infrastructure.mappers.RestauranteMapper;
import com.pragma.backend.infrastructure.repositories.RestauranteEntityRepository;

@SpringBootTest(properties = {
	    "eureka.client.enabled=false"
	})
public class RestauranteRepositoryAdapterTest {

	@MockBean
    private RestauranteEntityRepository restauranteEntityRepository;

    @Autowired
    private RestauranteRepositoryAdapter restauranteRepositoryAdapter;

    private Restaurante restaurante;
    private RestauranteEntity restauranteEntity;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante(1L, "Restaurante Test", "123456789", "Calle 123", "+57 3219876543", "http://logo.com", 1L);
        restauranteEntity = RestauranteMapper.toEntity(restaurante);
    }
    
    @Nested
    class SaveTests {
        
        @Test
        void shouldSaveRestaurante() {
            when(restauranteEntityRepository.save(any(RestauranteEntity.class))).thenReturn(restauranteEntity);

            Restaurante result = restauranteRepositoryAdapter.save(restaurante);

            assertEquals(restaurante.getNombre(), result.getNombre());
            verify(restauranteEntityRepository).save(any(RestauranteEntity.class));
        }
    }

    @Nested
    class FindByIdTests {

        @Test
        void shouldFindRestauranteById() {
            when(restauranteEntityRepository.findById(1L)).thenReturn(Optional.of(restauranteEntity));

            Optional<Restaurante> result = restauranteRepositoryAdapter.findById(1L);

            assertTrue(result.isPresent());
            assertEquals(restaurante.getNombre(), result.get().getNombre());
        }

        @Test
        void shouldReturnEmptyOptionalWhenRestauranteNotFound() {
            when(restauranteEntityRepository.findById(2L)).thenReturn(Optional.empty());

            Optional<Restaurante> result = restauranteRepositoryAdapter.findById(2L);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class FindByIdPropietarioTests {

        @Test
        void shouldFindRestauranteByIdPropietario() {
            when(restauranteEntityRepository.findByIdUsuarioPropietario(1L)).thenReturn(Optional.of(restauranteEntity));

            Optional<Restaurante> result = restauranteRepositoryAdapter.findByIdPropietario(1L);

            assertTrue(result.isPresent());
            assertEquals(restaurante.getNombre(), result.get().getNombre());
        }

        @Test
        void shouldReturnEmptyOptionalWhenRestauranteNotFoundByPropietario() {
            when(restauranteEntityRepository.findByIdUsuarioPropietario(2L)).thenReturn(Optional.empty());

            Optional<Restaurante> result = restauranteRepositoryAdapter.findByIdPropietario(2L);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class FindAllTests {

        @Test
        void shouldReturnAllRestaurantes() {
            Pageable pageable = Pageable.ofSize(10);
            Page<RestauranteEntity> page = new PageImpl<>(List.of(restauranteEntity));
            when(restauranteEntityRepository.findAllByOrderByNombreAsc(pageable)).thenReturn(page);

            Page<RestauranteInfo> result = restauranteRepositoryAdapter.findAll(pageable);

            assertEquals(1, result.getTotalElements());
            assertEquals(restaurante.getNombre(), result.getContent().get(0).getNombre());
        }
    }
}
