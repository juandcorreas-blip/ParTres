public class Postre extends Producto {

    private double pesoGramos;
    private double temperaturaAlmacenamiento;
    private boolean requiereRefrigeracion;

    public Postre(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion,
                  double pesoGramos, double temperaturaAlmacenamiento, boolean requiereRefrigeracion) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);
        this.pesoGramos = pesoGramos;
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public double getTemperaturaAlmacenamiento() {
        return temperaturaAlmacenamiento;
    }

    public void setTemperaturaAlmacenamiento(double temperaturaAlmacenamiento) {
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
    }

    public boolean isRequiereRefrigeracion() {

        return requiereRefrigeracion;
    }

    public void setRequiereRefrigeracion(boolean requiereRefrigeracion) {
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    @Override
    public double calcularPrecioVenta() {
        double precio = getPrecioBase() * 115 / 100;
        if (requiereRefrigeracion) {
            precio = precio + getPrecioBase() * 5 / 100;
        }
        return precio;
    }

    @Override
    public boolean cumpleCondicionesCalidad() {
        if (pesoGramos < 80 || pesoGramos > 500) {
            return false;
        }
        if (requiereRefrigeracion) {
            return temperaturaAlmacenamiento >= 0 && temperaturaAlmacenamiento <= 6;
        } else {
            return temperaturaAlmacenamiento >= 15 && temperaturaAlmacenamiento <= 30;
        }
    }

    @Override
    public CategoriaProducto getCategoria() {
        return null;
    }

    @Override
    public String toString() {
        return "[Postre] " + super.toString() + " | Peso: " + pesoGramos + " g | Temperatura: "
                + temperaturaAlmacenamiento + " °C | Refrigeración: " + (requiereRefrigeracion ? "Sí" : "No");
    }
}