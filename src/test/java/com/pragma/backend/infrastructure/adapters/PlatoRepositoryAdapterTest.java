package com.pragma.backend.infrastructure.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.infrastructure.entities.PlatoEntity;
import com.pragma.backend.infrastructure.mappers.PlatoMapper;
import com.pragma.backend.infrastructure.repositories.PlatoEntityRepository;

@SpringBootTest(properties = {
	    "eureka.client.enabled=false"
	})
public class PlatoRepositoryAdapterTest {

	@MockBean
	private PlatoEntityRepository platoEntityRepository;
	
	@Autowired
	private PlatoRepositoryAdapter platoRepositoryAdapter;
	
	private Restaurante restaurante;
	private Plato plato;
    private PlatoEntity platoEntity;

    @BeforeEach
    void setUp() {
    	restaurante = new Restaurante(1L, "Restaurante Ejemplo", "123456789", "Calle 123", "987654321", "http://logo.com", 100L);
        plato = new Plato(1L, "Plato Ejemplo", 500, "Descripción", "http://imagen.com", "Categoria", 1L, true, restaurante);
        platoEntity = PlatoMapper.toEntity(plato);
    }
    
    @Nested
    class SaveTests {
    	
        @Test
        void testSave_Success() {
            when(platoEntityRepository.save(any(PlatoEntity.class))).thenReturn(platoEntity);

            Plato result = platoRepositoryAdapter.save(plato);

            assertEquals(plato.getId(), result.getId());
            assertEquals(plato.getNombre(), result.getNombre());
            verify(platoEntityRepository).save(any(PlatoEntity.class));
        }
    }

    @Nested
    class FindByIdTests {
        
    	@Test
        void testFindById_Success() {
            when(platoEntityRepository.findById(anyLong())).thenReturn(Optional.of(platoEntity));

            Optional<Plato> result = platoRepositoryAdapter.findById(1L);

            assertTrue(result.isPresent());
            assertEquals(1L, result.get().getId());
        }

        @Test
        void testFindById_NotFound() {
            when(platoEntityRepository.findById(anyLong())).thenReturn(Optional.empty());

            Optional<Plato> result = platoRepositoryAdapter.findById(1L);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class FindByRestauranteEntityIdAndCategoriaTests {
        @Test
        void testFindByRestauranteEntityIdAndCategoria_Success() {
            Page<PlatoEntity> platoEntities = new PageImpl<>(List.of(platoEntity));
            when(platoEntityRepository.findByRestauranteEntityIdAndCategoria(anyLong(), any(), any(Pageable.class)))
                    .thenReturn(platoEntities);

            Page<Plato> result = platoRepositoryAdapter.findByRestauranteEntityIdAndCategoria(1L, "Categoria", Pageable.unpaged());

            assertEquals(1, result.getTotalElements());
            assertEquals(plato.getId(), result.getContent().get(0).getId());
        }
    }

    @Nested
    class FindByRestauranteEntityIdTests {
        @Test
        void testFindByRestauranteEntityId_Success() {
            Page<PlatoEntity> platoEntities = new PageImpl<>(List.of(platoEntity));
            when(platoEntityRepository.findByRestauranteEntityId(anyLong(), any(Pageable.class)))
                    .thenReturn(platoEntities);

            Page<Plato> result = platoRepositoryAdapter.findByRestauranteEntityId(1L, Pageable.unpaged());

            assertEquals(1, result.getTotalElements());
            assertEquals(plato.getId(), result.getContent().get(0).getId());
        }
    }
}
