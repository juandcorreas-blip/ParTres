import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Cliente cliente;
    private PlatoFuerte plato;
    private Bebida bebida;

    @BeforeEach
    void preparar() {
        cliente = new Cliente("123", "Ana Pérez", "3000000000", "ana@correo.com", "Calle 1");
        plato = new PlatoFuerte("P01", "Bandeja", "Plato de prueba", 10000, 30, 500, TipoPreparacion.FRITO, true);
        bebida = new Bebida("B01", "Limonada", "Bebida de prueba", 5000, 5, 400, 5, TipoBebida.FRIA, false);
    }

    @Test
    void pedidoNuevoQuedaPendiente() {
        Pedido pedido = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.CONSUMO_EN_RESTAURANTE);
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
    }

    @Test
    void valorDeProductosSumaTodosLosDetalles() {
        Pedido pedido = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.CONSUMO_EN_RESTAURANTE);
        pedido.agregarDetalle(plato, 2);   // 24.000
        pedido.agregarDetalle(bebida, 1);  // 5.500
        assertEquals(29500, pedido.calcularValorProductos(), 0.001);
    }

    @Test
    void pedidoEnRestauranteNoTieneCostoDeDomicilio() {
        Pedido pedido = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.CONSUMO_EN_RESTAURANTE);
        pedido.agregarDetalle(plato, 1);
        assertEquals(12000, pedido.calcularValorTotal(), 0.001);
    }

    @Test
    void totalDelPedidoADomicilioIncluyeElCostoDelDomicilio() {
        Pedido pedido = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.DOMICILIO);
        pedido.agregarDetalle(plato, 1);
        pedido.setDomicilio(new RapidoExpress().solicitarServicio(10)); // 20.000
        assertEquals(32000, pedido.calcularValorTotal(), 0.001);
    }

    @Test
    void domicilioNoAceptadoNoSumaAlTotal() {
        Pedido pedido = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.DOMICILIO);
        pedido.agregarDetalle(plato, 1);
        pedido.setDomicilio(new RapidoExpress().solicitarServicio(30));
        assertEquals(12000, pedido.calcularValorTotal(), 0.001);
    }
}