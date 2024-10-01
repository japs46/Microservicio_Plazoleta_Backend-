package com.pragma.backend.infrastructure.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.pragma.backend.infrastructure.entities.RestauranteEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
public class RestauranteEntityRepositoryTest {

	@Autowired
	private RestauranteEntityRepository restauranteEntityRepository;
	
	@Nested
    class SaveAndFindByIdTests {

        @Test
        void testSaveAndFindById() {
        	
            RestauranteEntity restaurante = new RestauranteEntity(null, "Restaurante Prueba", "123456789", 
                    "Calle Falsa 123", "987654321", "http://logo.com", 1L);
            
            RestauranteEntity savedRestaurante = restauranteEntityRepository.save(restaurante);
            
            Optional<RestauranteEntity> result = restauranteEntityRepository.findById(savedRestaurante.getId());

            assertNotNull(result); 
            assertThat(result).isPresent(); 
            assertEquals("Restaurante Prueba", result.get().getNombre()); 
            assertEquals("123456789", result.get().getNit()); 
            assertEquals(1L, result.get().getIdUsuarioPropietario()); 
        }
    }

    @Nested
    class FindByIdUsuarioPropietarioTests {

        @Test
        void testFindByIdUsuarioPropietario() {
            RestauranteEntity restaurante = new RestauranteEntity(null, "Restaurante Usuario Propietario", 
                    "987654321", "Calle 456", "654321987", "http://logo2.com", 2L);
            restauranteEntityRepository.save(restaurante);

            Optional<RestauranteEntity> result = restauranteEntityRepository.findByIdUsuarioPropietario(2L);

            assertNotNull(result);
            assertThat(result).isPresent(); 
            assertEquals("Restaurante Usuario Propietario", result.get().getNombre()); 
            assertEquals(2L, result.get().getIdUsuarioPropietario());
        }

        @Test
        void testFindByIdUsuarioPropietario_NotFound() {
            Optional<RestauranteEntity> result = restauranteEntityRepository.findByIdUsuarioPropietario(999L);

            assertNotNull(result);
            assertThat(result).isNotPresent();
        }
    }

    @Nested
    class FindAllByOrderByNombreAscTests {

        @Test
        void testFindAllByOrderByNombreAsc() {
            restauranteEntityRepository.save(new RestauranteEntity(null, "B Restaurante", "111111111", "Calle B", "111111111", "http://logo1.com", 3L));
            restauranteEntityRepository.save(new RestauranteEntity(null, "A Restaurante", "222222222", "Calle A", "222222222", "http://logo2.com", 4L));
            restauranteEntityRepository.save(new RestauranteEntity(null, "C Restaurante", "333333333", "Calle C", "333333333", "http://logo3.com", 5L));

            Pageable pageable = PageRequest.of(0, 10);

            Page<RestauranteEntity> result = restauranteEntityRepository.findAllByOrderByNombreAsc(pageable);

            assertNotNull(result);
            assertThat(result).isNotEmpty();
            assertEquals(3, result.getTotalElements());

            assertEquals("A Restaurante", result.getContent().get(0).getNombre());
            assertEquals("B Restaurante", result.getContent().get(1).getNombre());
            assertEquals("C Restaurante", result.getContent().get(2).getNombre());
        }
    }
}
