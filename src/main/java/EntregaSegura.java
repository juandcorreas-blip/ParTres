public class EntregaSegura extends EmpresaDomicilio {

    public EntregaSegura() {
        super("EntregaSegura", 20);
    }

    // $7.000 más $1.000 por kilómetro, y $3.000 adicionales si pasa de 5 km
    @Override
    public double calcularCosto(double distanciaKm) {
        double costo = 7000 + 1000 * distanciaKm;
        if (distanciaKm > 5) {
            costo = costo + 3000;
        }
        return costo;
    }
}
