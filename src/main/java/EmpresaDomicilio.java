public abstract class EmpresaDomicilio {

    private String nombre;
    private double distanciaMaxima; // en km

    public EmpresaDomicilio(String nombre, double distanciaMaxima) {
        this.nombre = nombre;
        this.distanciaMaxima = distanciaMaxima;
    }

    // Cada empresa tiene su propia tarifa
    public abstract double calcularCosto(double distanciaKm);

    // La empresa solo acepta recorridos hasta su distancia máxima
    public boolean aceptaDistancia(double distanciaKm) {
        return distanciaKm > 0 && distanciaKm <= distanciaMaxima;
    }

    // Procedimiento común para pedirle el servicio a cualquiera de las dos empresas
    public Domicilio solicitarServicio(double distanciaKm) {
        boolean aceptado = aceptaDistancia(distanciaKm);
        double costo = 0;
        if (aceptado) {
            costo = calcularCosto(distanciaKm);
        }
        return new Domicilio(this, distanciaKm, costo, aceptado);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDistanciaMaxima() {
        return distanciaMaxima;
    }

    public void setDistanciaMaxima(double distanciaMaxima) {
        this.distanciaMaxima = distanciaMaxima;
    }

    @Override
    public String toString() {
        return nombre + " (hasta " + distanciaMaxima + " km)";
    }
}
