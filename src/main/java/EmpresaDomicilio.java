public abstract class EmpresaDomicilio {

    private String nombre;
    private double distanciaMaxima;

    public EmpresaDomicilio(String nombre, double distanciaMaxima) {
        this.nombre = nombre;
        this.distanciaMaxima = distanciaMaxima;
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
}