public class PlatoFuerte extends Producto {

    private double pesoGramos;
    private TipoPreparacion tipoPreparacion;
    private boolean coccionAprobada;

    public PlatoFuerte(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion,
                       double pesoGramos, TipoPreparacion tipoPreparacion, boolean coccionAprobada) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);
        this.pesoGramos = pesoGramos;
        this.tipoPreparacion = tipoPreparacion;
        this.coccionAprobada = coccionAprobada;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public TipoPreparacion getTipoPreparacion() {
        return tipoPreparacion;
    }

    public void setTipoPreparacion(TipoPreparacion tipoPreparacion) {
        this.tipoPreparacion = tipoPreparacion;
    }

    public boolean isCoccionAprobada() {
        return coccionAprobada;
    }

    public void setCoccionAprobada(boolean coccionAprobada) {
        this.coccionAprobada = coccionAprobada;
    }

    @Override
    public double calcularPrecioVenta() {
        return getPrecioBase() * 120 / 100;
    }


    @Override
    public boolean cumpleCondicionesCalidad() {
        return pesoGramos >= 200 && pesoGramos <= 900
                && getTiempoPreparacion() >= 10 && getTiempoPreparacion() <= 60
                && coccionAprobada;
    }

    @Override
    public CategoriaProducto getCategoria() {
        return CategoriaProducto.PLATO_FUERTE;
    }

    @Override
    public String toString() {
        return "[Plato fuerte] " + super.toString() + " | Peso: " + pesoGramos + " g | Preparación: "
                + tipoPreparacion + " | Cocción aprobada: " + (coccionAprobada ? "Sí" : "No");
    }
}