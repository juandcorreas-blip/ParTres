public class Bebida extends Producto {

    private double volumenMl;
    private double temperatura;
    private TipoBebida tipoBebida;
    private boolean requierePreparacionEspecial;

    public Bebida(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion,
                  double volumenMl, double temperatura, TipoBebida tipoBebida, boolean requierePreparacionEspecial) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);
        this.volumenMl = volumenMl;
        this.temperatura = temperatura;
        this.tipoBebida = tipoBebida;
        this.requierePreparacionEspecial = requierePreparacionEspecial;
    }

    public double getVolumenMl() {
        return volumenMl;
    }

    public void setVolumenMl(double volumenMl) {
        this.volumenMl = volumenMl;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public TipoBebida getTipoBebida() {
        return tipoBebida;
    }

    public void setTipoBebida(TipoBebida tipoBebida) {
        this.tipoBebida = tipoBebida;
    }

    public boolean isRequierePreparacionEspecial() {
        return requierePreparacionEspecial;
    }

    public void setRequierePreparacionEspecial(boolean requierePreparacionEspecial) {
        this.requierePreparacionEspecial = requierePreparacionEspecial;
    }

    @Override
    public double calcularPrecioVenta() {
        double precio = getPrecioBase() * 110 / 100;
        if (requierePreparacionEspecial) {
            precio = precio + 3000;
        }
        return precio;
    }

    @Override
    public boolean cumpleCondicionesCalidad() {
        if (volumenMl < 150 || volumenMl > 1500) {
            return false;
        }
        if (tipoBebida == TipoBebida.FRIA) {
            return temperatura >= 0 && temperatura <= 10;
        } else {
            return temperatura >= 55 && temperatura <= 85;
        }
    }

    @Override
    public String toString() {
        return "[Bebida] " + super.toString() + " | Volumen: " + volumenMl + " ml | Temperatura: " + temperatura
                + " °C | Tipo: " + tipoBebida + " | Preparación especial: " + (requierePreparacionEspecial ? "Sí" : "No");
    }
}
