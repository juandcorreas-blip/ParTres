import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {

    private Restaurante restaurante;
    private Cliente cliente;
    private PlatoFuerte plato;     // precio de venta $12.000
    private PlatoFuerte platoMalo; // cocción no aprobada

    @BeforeEach
    void preparar() {
        restaurante = new Restaurante("Sabor & Tradición", "900123456-7", "Armenia, Quindío");
        cliente = new Cliente("123", "Ana Pérez", "3000000000", "ana@correo.com", "Calle 1");
        plato = new PlatoFuerte("P01", "Bandeja", "Plato de prueba", 10000, 30, 500, TipoPreparacion.FRITO, true);
        platoMalo = new PlatoFuerte("P02", "Pollo", "Plato sin cocción", 10000, 30, 500, TipoPreparacion.ASADO, false);
        restaurante.registrarProducto(plato);
        restaurante.registrarProducto(platoMalo);
        restaurante.registrarCliente(cliente);
    }

    @Test
    void noSeRegistraUnProductoConCodigoRepetido() {
        PlatoFuerte repetido = new PlatoFuerte("P01", "Otro", "Mismo código", 9000, 30, 500,
                TipoPreparacion.HORNEADO, true);
        assertFalse(restaurante.registrarProducto(repetido));
        assertEquals(2, restaurante.getProductos().size());
    }

    @Test
    void noSeRegistraUnClienteConDocumentoRepetido() {
        Cliente repetido = new Cliente("123", "Otra Persona", "3111111111", "otra@correo.com", "Calle 2");
        assertFalse(restaurante.registrarCliente(repetido));
        assertEquals(1, restaurante.getClientes().size());
    }

    @Test
    void confirmarPedidoValidoLoGuardaEnElHistorialYNoSePuedeConfirmarDeNuevo() {
        Pedido pedido = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.CONSUMO_EN_RESTAURANTE);
        pedido.agregarDetalle(plato, 2);
        restaurante.registrarPedido(pedido);

        assertTrue(restaurante.confirmarPedido("PED1"));
        assertEquals(EstadoPedido.CONFIRMADO, pedido.getEstado());
        assertEquals(1, cliente.getHistorialPedidos().size());
        assertFalse(restaurante.confirmarPedido("PED1"));
    }

    @Test
    void noSeConfirmaUnPedidoConProductoQueNoCumpleCalidad() {
        Pedido pedido = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.CONSUMO_EN_RESTAURANTE);
        pedido.agregarDetalle(platoMalo, 1);
        restaurante.registrarPedido(pedido);

        assertFalse(restaurante.confirmarPedido("PED1"));
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
    }

    @Test
    void ingresosDelRestauranteNoIncluyenDomiciliosNiPedidosCancelados() {
        // Pedido 1: en el restaurante, $24.000
        Pedido pedido1 = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.CONSUMO_EN_RESTAURANTE);
        pedido1.agregarDetalle(plato, 2);
        restaurante.registrarPedido(pedido1);
        restaurante.confirmarPedido("PED1");

        // Pedido 2: a domicilio, $12.000 en productos y $20.000 de domicilio
        Pedido pedido2 = new Pedido("PED2", LocalDate.now(), cliente, ModalidadEntrega.DOMICILIO);
        pedido2.agregarDetalle(plato, 1);
        pedido2.setDomicilio(new RapidoExpress().solicitarServicio(10));
        restaurante.registrarPedido(pedido2);
        restaurante.confirmarPedido("PED2");

        // Pedido 3: se confirma y luego se cancela, no cuenta
        Pedido pedido3 = new Pedido("PED3", LocalDate.now(), cliente, ModalidadEntrega.CONSUMO_EN_RESTAURANTE);
        pedido3.agregarDetalle(plato, 5);
        restaurante.registrarPedido(pedido3);
        restaurante.confirmarPedido("PED3");
        restaurante.actualizarEstadoPedido("PED3", EstadoPedido.CANCELADO);

        assertEquals(36000, restaurante.calcularIngresosRestaurante(), 0.001);
        assertEquals(20000, restaurante.calcularIngresosDomicilios(), 0.001);
    }
}
