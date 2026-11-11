import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Cliente cliente;
    private Pedido pedido;

    @BeforeEach
    void preparar() {
        cliente = new Cliente("123", "Ana Pérez", "3000000000", "ana@correo.com", "Calle 1");
        pedido = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.CONSUMO_EN_RESTAURANTE);
    }

    @Test
    void clienteNuevoNoTienePedidosEnElHistorial() {
        assertEquals(0, cliente.getHistorialPedidos().size());
    }

    @Test
    void agregarPedidoNuevoLoGuardaEnElHistorial() {
        assertTrue(cliente.agregarPedido(pedido));
        assertEquals(1, cliente.getHistorialPedidos().size());
    }

    @Test
    void noSePuedeAgregarDosVecesElMismoPedido() {
        cliente.agregarPedido(pedido);
        assertFalse(cliente.agregarPedido(pedido));
        assertEquals(1, cliente.getHistorialPedidos().size());
    }

    @Test
    void buscarPedidoExistenteLoEncuentra() {
        cliente.agregarPedido(pedido);
        assertSame(pedido, cliente.buscarPedido("PED1"));
    }

    @Test
    void buscarPedidoInexistenteDevuelveNull() {
        assertNull(cliente.buscarPedido("PED99"));
    }
}