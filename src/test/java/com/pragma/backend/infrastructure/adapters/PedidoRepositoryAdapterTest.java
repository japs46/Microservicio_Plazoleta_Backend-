package com.pragma.backend.infrastructure.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pragma.backend.domain.models.DetallePedido;
import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.infrastructure.entities.PedidoEntity;
import com.pragma.backend.infrastructure.mappers.PedidoMapper;
import com.pragma.backend.infrastructure.repositories.PedidoEntityRepository;

@SpringBootTest(properties = {
	    "eureka.client.enabled=false"
	})
public class PedidoRepositoryAdapterTest {

    @MockBean
    private PedidoEntityRepository pedidoEntityRepository;

    @Autowired
    private PedidoRepositoryAdapter pedidoRepositoryAdapter;

    private Restaurante restaurante;
    private Plato plato;
    private DetallePedido detallePedido;
    private List<DetallePedido> detallesPedido;
    private Pedido pedido;
    private PedidoEntity pedidoEntity;

    @BeforeEach
    void setUp() {
    	
    	restaurante = new Restaurante(
                1L, "Restaurante Prueba", "123456789", "Calle 123", "1234567", 
                "http://logo.com/logo.png", 100L);

        plato = new Plato(
                1L, "Plato de prueba", 20000, "Descripción del plato", "http://imagen.com/plato.png", 
                "Categoría", 1L, true, restaurante);
        
        detallePedido = new DetallePedido(1L, plato, null, 2);
        detallesPedido = List.of(detallePedido);
    	
        pedido = new Pedido(1L, 1L, restaurante, detallesPedido, EstadoPedido.PENDIENTE, new Date(),null);
        pedidoEntity = PedidoMapper.toEntity(pedido);
        
    }

    @Nested
    class SaveTests {
        @Test
        void testSave_Success() {
            when(pedidoEntityRepository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);

            Pedido result = pedidoRepositoryAdapter.save(pedido);

            assertEquals(pedido.getId(), result.getId());
            assertEquals(pedido.getIdCliente(), result.getIdCliente());
            verify(pedidoEntityRepository).save(any(PedidoEntity.class));
        }
    }

    @Nested
    class FindByIdClienteTests {

        @Test
        void testFindByidCliente_Success() {
            // Simular el comportamiento de encontrar pedidos por idCliente
            when(pedidoEntityRepository.findByidCliente(anyLong())).thenReturn(List.of(pedidoEntity));

            List<Pedido> result = pedidoRepositoryAdapter.findByidCliente(1L);

            assertEquals(1, result.size());
            assertEquals(pedido.getId(), result.get(0).getId());
        }

        @Test
        void testFindByidCliente_EmptyResult() {
            // Simular que no se encuentran pedidos
            when(pedidoEntityRepository.findByidCliente(anyLong())).thenReturn(List.of());

            List<Pedido> result = pedidoRepositoryAdapter.findByidCliente(1L);

            assertTrue(result.isEmpty());
        }
    }
}
