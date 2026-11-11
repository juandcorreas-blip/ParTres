import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatoFuerteTest {

    // Plato de prueba con precio base de $10.000
    private PlatoFuerte crearPlato(double peso, int tiempo, boolean coccion) {
        return new PlatoFuerte("P01", "Bandeja", "Plato de prueba", 10000, tiempo, peso,
                TipoPreparacion.A_LA_PLANCHA, coccion);
    }

    @Test
    void precioDeVentaTieneIncrementoDel20Porciento() {
        assertEquals(12000, crearPlato(500, 30, true).calcularPrecioVenta(), 0.001);
    }

    @Test
    void cumpleCalidadConValoresDentroDelRango() {
        assertTrue(crearPlato(500, 30, true).cumpleCondicionesCalidad());
    }

    @Test
    void noCumpleCalidadSiPesaMenosDe200Gramos() {
        assertFalse(crearPlato(199, 30, true).cumpleCondicionesCalidad());
    }

    @Test
    void noCumpleCalidadSiTardaMasDe60Minutos() {
        assertFalse(crearPlato(500, 61, true).cumpleCondicionesCalidad());
    }

    @Test
    void noCumpleCalidadSiLaCoccionNoFueAprobada() {
        assertFalse(crearPlato(500, 30, false).cumpleCondicionesCalidad());
    }
}
