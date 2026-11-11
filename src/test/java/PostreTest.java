import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostreTest {

    // Postre de prueba con precio base de $10.000
    private Postre crearPostre(double peso, double temperatura, boolean refrigeracion) {
        return new Postre("D01", "Postre", "Postre de prueba", 10000, 5, peso, temperatura, refrigeracion);
    }

    @Test
    void precioDeVentaTieneIncrementoDel15Porciento() {
        assertEquals(11500, crearPostre(200, 20, false).calcularPrecioVenta(), 0.001);
    }

    @Test
    void postreRefrigeradoSumaOtro5PorcientoDelPrecioBase() {
        assertEquals(12000, crearPostre(200, 4, true).calcularPrecioVenta(), 0.001);
    }

    @Test
    void postreRefrigeradoEntre0Y6GradosCumpleCalidad() {
        assertTrue(crearPostre(200, 4, true).cumpleCondicionesCalidad());
    }

    @Test
    void postreRefrigeradoAMasDe6GradosNoCumpleCalidad() {
        assertFalse(crearPostre(200, 10, true).cumpleCondicionesCalidad());
    }

    @Test
    void postreQueNoLlegaA80GramosNoCumpleCalidad() {
        assertFalse(crearPostre(50, 20, false).cumpleCondicionesCalidad());
    }
}