public class RapidoExpress extends EmpresaDomicilio {

    public RapidoExpress() {
        super("RápidoExpress", 12);
    }

    // $5.000 de tarifa base más $1.500 por kilómetro
    @Override
    public double calcularCosto(double distanciaKm) {
        return 5000 + 1500 * distanciaKm;
    }
}
