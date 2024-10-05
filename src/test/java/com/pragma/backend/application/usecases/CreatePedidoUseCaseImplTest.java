package com.pragma.backend.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pragma.backend.domain.models.DetallePedido;
import com.pragma.backend.domain.models.DetallePedidoRequest;
import com.pragma.backend.domain.models.EstadoPedido;
import com.pragma.backend.domain.models.Pedido;
import com.pragma.backend.domain.models.Plato;
import com.pragma.backend.domain.models.RequestPedido;
import com.pragma.backend.domain.models.Restaurante;
import com.pragma.backend.domain.ports.in.RetrievePedidoUseCase;
import com.pragma.backend.domain.ports.in.RetrievePlatoUseCase;
import com.pragma.backend.domain.ports.in.RetrieveRestauranteUseCase;
import com.pragma.backend.domain.ports.out.PedidoRepositoryPort;

@SpringBootTest(properties = {
	    "eureka.client.enabled=false"
	})
public class CreatePedidoUseCaseImplTest {

	@MockBean
    private PedidoRepositoryPort pedidoRepositoryPort;

	@MockBean
    private @Qualifier("retrieveRestauranteUseCaseImpl") RetrieveRestauranteUseCase retrieveRestauranteUseCase;

	@MockBean
    private @Qualifier("retrievePlatoUseCaseImpl") RetrievePlatoUseCase retrievePlatoUseCase;

	@MockBean
    private @Qualifier("retrievePedidoUseCaseImpl") RetrievePedidoUseCase retrievePedidoUseCase;

    @Autowired
    private CreatePedidoUseCaseImpl createPedidoUseCase;

    @BeforeEach
    void setUp() {
       
    }

    @Test
    void testCreatePedido_Success() {
        RequestPedido requestPedido = new RequestPedido(1L, 1L, 1L, List.of(new DetallePedidoRequest(1L, 2)), "PENDIENTE", new Date());

        Restaurante restaurante = new Restaurante(1L, "Restaurante Prueba", "123456789", "Calle 123", "1234567", "http://logo.com/logo.png", 100L);
        Plato plato = new Plato(1L, "Plato Prueba", 20000, "Descripción", "http://imagen.com/plato.png", "Categoría", 1L, true, restaurante);
        
        DetallePedido detallePedido = new DetallePedido(1L, plato, null, 2);
        List<DetallePedido> detallesPedido = List.of(detallePedido);
        Pedido pedidoBd = new Pedido(1L, 1L, restaurante, detallesPedido, EstadoPedido.PENDIENTE, new Date(),null);
        
        when(retrievePedidoUseCase.buscarPedidosPorCliente(1L)).thenReturn(List.of());
        when(retrieveRestauranteUseCase.obtenerRestaurantePorId(1L)).thenReturn(restaurante);
        when(retrievePlatoUseCase.obtenerPlatoPorId(1L)).thenReturn(plato);
        when(pedidoRepositoryPort.save(any(Pedido.class))).thenReturn(pedidoBd);

        Pedido result = createPedidoUseCase.createPedido(requestPedido, "Bearer token");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(EstadoPedido.PENDIENTE, result.getEstado());
        assertEquals(1, result.getPlatos().size());
        assertEquals(2, result.getPlatos().get(0).getCantidad());
    }

    @Test
    void testCreatePedido_ThrowsException_PedidoEnProceso() {
        List<Pedido> pedidosEnProceso = List.of(
            new Pedido(1L, 1L, null, null, EstadoPedido.PENDIENTE, new Date(),null)
        );

        when(retrievePedidoUseCase.buscarPedidosPorCliente(1L)).thenReturn(pedidosEnProceso);

        RequestPedido requestPedido = new RequestPedido(1L, 1L, 1L, List.of(new DetallePedidoRequest(1L, 2)), "PENDIENTE", new Date());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            createPedidoUseCase.createPedido(requestPedido, "Bearer token");
        });

        assertEquals("El cliente no puede realizar pedido porque tiene pedidos en proceso.", exception.getMessage());
    }

    @Test
    void testCreatePedido_ThrowsException_CantidadMinimaPlatos() {
        RequestPedido requestPedido = new RequestPedido(1L, 1L, 1L, List.of(), "PENDIENTE", new Date());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            createPedidoUseCase.createPedido(requestPedido, "Bearer token");
        });

        assertEquals("La cantidad mínima requerida para realizar un pedido es de 1 plato.", exception.getMessage());
    }

    @Test
    void testCreatePedido_ThrowsException_CantidadPlatoNoValida() {
        RequestPedido requestPedido = new RequestPedido(1L, 1L, 1L, List.of(new DetallePedidoRequest(1L, 0)), "PENDIENTE", new Date());

        Restaurante restaurante = new Restaurante(1L, "Restaurante Prueba", "123456789", "Calle 123", "1234567", "http://logo.com/logo.png", 100L);
        when(retrieveRestauranteUseCase.obtenerRestaurantePorId(1L)).thenReturn(restaurante);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            createPedidoUseCase.createPedido(requestPedido, "Bearer token");
        });

        assertEquals("La cantidad del plato debe ser un número positivo. Id del plato: 1", exception.getMessage());
    }
}
