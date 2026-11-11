import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetallePedidoTest {

    private PlatoFuerte plato = new PlatoFuerte("P01", "Bandeja", "Plato de prueba", 10000, 30, 500,
            TipoPreparacion.FRITO, true);
    private Bebida bebida = new Bebida("B01", "Limonada", "Bebida de prueba", 5000, 5, 400, 5,
            TipoBebida.FRIA, false);

    @Test
    void subtotalDePlatoEsPrecioDeVentaPorCantidad() {
        assertEquals(24000, new DetallePedido(plato, 2).calcularSubtotal(), 0.001);
    }

    @Test
    void subtotalDeBebidaEsPrecioDeVentaPorCantidad() {
        assertEquals(16500, new DetallePedido(bebida, 3).calcularSubtotal(), 0.001);
    }

    @Test
    void cantidadMayorQueCeroEsValida() {
        assertTrue(new DetallePedido(plato, 1).cantidadValida());
    }

    @Test
    void cantidadCeroNoEsValida() {
        assertFalse(new DetallePedido(plato, 0).cantidadValida());
    }

    @Test
    void cantidadNegativaNoEsValida() {
        assertFalse(new DetallePedido(plato, -2).cantidadValida());
    }
}
