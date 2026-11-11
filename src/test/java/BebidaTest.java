import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BebidaTest {

    // Bebida de prueba con precio base de $5.000
    private Bebida crearBebida(double volumen, double temperatura, TipoBebida tipo, boolean especial) {
        return new Bebida("B01", "Bebida", "Bebida de prueba", 5000, 5, volumen, temperatura, tipo, especial);
    }

    @Test
    void precioDeVentaTieneIncrementoDel10Porciento() {
        assertEquals(5500, crearBebida(400, 5, TipoBebida.FRIA, false).calcularPrecioVenta(), 0.001);
    }

    @Test
    void precioDeVentaSumaTresMilPesosSiRequierePreparacionEspecial() {
        assertEquals(8500, crearBebida(400, 5, TipoBebida.FRIA, true).calcularPrecioVenta(), 0.001);
    }

    @Test
    void bebidaFriaDentroDelRangoCumpleCalidad() {
        assertTrue(crearBebida(400, 5, TipoBebida.FRIA, false).cumpleCondicionesCalidad());
    }

    @Test
    void bebidaCalienteFueraDelRangoNoCumpleCalidad() {
        assertFalse(crearBebida(300, 90, TipoBebida.CALIENTE, false).cumpleCondicionesCalidad());
    }

    @Test
    void volumenMenorDe150MlNoCumpleCalidad() {
        assertFalse(crearBebida(100, 5, TipoBebida.FRIA, false).cumpleCondicionesCalidad());
    }
}