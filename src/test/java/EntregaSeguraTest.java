import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntregaSeguraTest {

    private EntregaSegura empresa = new EntregaSegura();

    @Test
    void hastaCincoKilometrosNoTieneAdicional() {
        assertEquals(12000, empresa.calcularCosto(5), 0.001);
    }

    @Test
    void conMasDeCincoKilometrosSumaTresMilPesos() {
        assertEquals(20000, empresa.calcularCosto(10), 0.001);
    }

    @Test
    void aceptaRecorridoDeExactamente20Km() {
        assertTrue(empresa.aceptaDistancia(20));
    }

    @Test
    void noAceptaRecorridoMayorA20Km() {
        assertFalse(empresa.aceptaDistancia(21));
    }

    @Test
    void solicitarServicioDentroDelLimiteQuedaAceptadoConSuCosto() {
        Domicilio domicilio = empresa.solicitarServicio(4);
        assertTrue(domicilio.isAceptado());
        assertEquals(11000, domicilio.getCosto(), 0.001);
    }
}