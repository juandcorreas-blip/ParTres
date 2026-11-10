public class Domicilio {

    private EmpresaDomicilio empresa;
    private double distanciaKm;
    private double costo;
    private boolean aceptado;

    public Domicilio(EmpresaDomicilio empresa, double distanciaKm, double costo, boolean aceptado) {
        this.empresa = empresa;
        this.distanciaKm = distanciaKm;
        this.costo = costo;
        this.aceptado = aceptado;
    }

    public EmpresaDomicilio getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDomicilio empresa) {
        this.empresa = empresa;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    @Override
    public String toString() {
        return "Empresa: " + empresa.getNombre() + " | Distancia: " + distanciaKm + " km | Costo: $" + costo
                + " | Aceptado: " + (aceptado ? "Sí" : "No");
    }
}
