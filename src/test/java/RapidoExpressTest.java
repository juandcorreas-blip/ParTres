import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RapidoExpressTest {

    private RapidoExpress empresa = new RapidoExpress();

    @Test
    void costoEs5000MasMil500PorKilometro() {
        assertEquals(20000, empresa.calcularCosto(10), 0.001);
    }

    @Test
    void aceptaRecorridoDeExactamente12Km() {
        assertTrue(empresa.aceptaDistancia(12));
    }

    @Test
    void noAceptaRecorridoMayorA12Km() {
        assertFalse(empresa.aceptaDistancia(13));
    }

    @Test
    void solicitarServicioDentroDelLimiteQuedaAceptadoConSuCosto() {
        Domicilio domicilio = empresa.solicitarServicio(8);
        assertTrue(domicilio.isAceptado());
        assertEquals(17000, domicilio.getCosto(), 0.001);
    }

    @Test
    void solicitarServicioFueraDelLimiteNoSeAceptaYNoTieneCosto() {
        Domicilio domicilio = empresa.solicitarServicio(15);
        assertFalse(domicilio.isAceptado());
        assertEquals(0, domicilio.getCosto(), 0.001);
    }
}