import java.util.ArrayList;

public class Cliente {

    private String documento;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private String direccion;
    private ArrayList<Pedido> historialPedidos;

    public Cliente(String documento, String nombreCompleto, String telefono, String correo, String direccion) {
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.historialPedidos = new ArrayList<>();
    }
    public boolean agregarPedido(Pedido pedido) {
        if (buscarPedido(pedido.getCodigo()) != null) {
            return false;
        }
        historialPedidos.add(pedido);
        return true;
    }

    public Pedido buscarPedido(String codigo) {
        for (Pedido pedido : historialPedidos) {
            if (pedido.getCodigo().equals(codigo)) {
                return pedido;
            }
        }
        return null;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }

    public void setHistorialPedidos(ArrayList<Pedido> historialPedidos) {
        this.historialPedidos = historialPedidos;
    }
    @Override
    public String toString() {
        return "Documento: " + documento + " | Nombre: " + nombreCompleto + " | Teléfono: " + telefono
                + " | Correo: " + correo + " | Dirección: " + direccion;
    }
}
