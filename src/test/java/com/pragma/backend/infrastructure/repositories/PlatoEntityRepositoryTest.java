package com.pragma.backend.infrastructure.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.pragma.backend.infrastructure.entities.PlatoEntity;
import com.pragma.backend.infrastructure.entities.RestauranteEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
public class PlatoEntityRepositoryTest {

	@Autowired
    private PlatoEntityRepository platoEntityRepository;
	
	@Autowired
    private RestauranteEntityRepository restauranteEntityRepository;

	@Nested
	@Order(1)
    class SaveTests {

        @Test
        void testSaveAndFindById() {
            RestauranteEntity restauranteEntity = new RestauranteEntity(null, "Restaurante Ejemplo", "123456789", "Calle 123", "987654321", "http://logo.com", 100L);
            RestauranteEntity restauranteEntityBd = restauranteEntityRepository.save(restauranteEntity);

            PlatoEntity plato = new PlatoEntity(null, "Plato Ejemplo", 10000, "Descripción del plato", 
                "http://imagen.com", "Categoria Ejemplo", true, restauranteEntityBd);

            PlatoEntity platoBd=platoEntityRepository.save(plato);

            Optional<PlatoEntity> result = platoEntityRepository.findById(platoBd.getId());

            assertThat(result).isPresent();
            assertThat(result.get().getNombre()).isEqualTo("Plato Ejemplo");
        }
    }

    @Nested
    class FindByRestauranteEntityIdTests {

        @Test
        void testFindByRestauranteEntityId() {
            RestauranteEntity restauranteEntity = new RestauranteEntity(null, "Restaurante Ejemplo", "123456789", "Calle 123", "987654321", "http://logo.com", 100L);
            RestauranteEntity restauranteEntityBd = restauranteEntityRepository.save(restauranteEntity); // Guardar el restaurante

            PlatoEntity plato = new PlatoEntity(null, "Plato Otro", 15000, "Descripción del plato otro", 
                "http://imagen2.com", "Categoria Otro", true, restauranteEntityBd);
            PlatoEntity platoBd = platoEntityRepository.save(plato);

            Pageable pageable = PageRequest.of(0, 10);

            Page<PlatoEntity> result = platoEntityRepository.findByRestauranteEntityId(platoBd.getRestauranteEntity().getId(), pageable);

            assertThat(result).isNotEmpty();
            assertThat(result.getContent().get(0).getNombre()).isEqualTo("Plato Otro");
        }

        @Test
        void testFindByRestauranteEntityId_NotFound() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<PlatoEntity> result = platoEntityRepository.findByRestauranteEntityId(999L, pageable); // ID de Restaurante no existente

            assertThat(result).isEmpty();
        }
    }

    @Nested
    class FindByRestauranteEntityIdAndCategoriaTests {

        @Test
        void testFindByRestauranteEntityIdAndCategoria() {
            RestauranteEntity restauranteEntity = new RestauranteEntity(null, "Restaurante Ejemplo", "123456789", "Calle 123", "987654321", "http://logo.com", 100L);
            RestauranteEntity restauranteEntityBd = restauranteEntityRepository.save(restauranteEntity); // Guardar el restaurante

            PlatoEntity plato = new PlatoEntity(null, "Plato Categoria", 20000, "Descripción con categoría", 
                "http://imagen3.com", "Categoria Especial", true, restauranteEntityBd);
            PlatoEntity platoBd=platoEntityRepository.save(plato);

            Pageable pageable = PageRequest.of(0, 10);

            Page<PlatoEntity> result = platoEntityRepository.findByRestauranteEntityIdAndCategoria(platoBd.getRestauranteEntity().getId(), "Categoria Especial", pageable);

            assertThat(result).isNotEmpty();
            assertThat(result.getContent().get(0).getNombre()).isEqualTo("Plato Categoria");
        }

        @Test
        void testFindByRestauranteEntityIdAndCategoria_NotFound() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<PlatoEntity> result = platoEntityRepository.findByRestauranteEntityIdAndCategoria(999L, "Categoria Inexistente", pageable);

            assertThat(result).isEmpty();
        }
    }
}
