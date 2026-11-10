import java.util.ArrayList;

public class Restaurante {

    private String nombreComercial;
    private String nit;
    private String direccion;
    private ArrayList<Producto> productos;
    private ArrayList<Cliente> clientes;
    private ArrayList<Pedido> pedidos;
    private ArrayList<EmpresaDomicilio> empresasDomicilio;

    public Restaurante(String nombreComercial, String nit, String direccion) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.productos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.empresasDomicilio = new ArrayList<>();
        this.empresasDomicilio.add(new RapidoExpress());
        this.empresasDomicilio.add(new EntregaSegura());
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public ArrayList<EmpresaDomicilio> getEmpresasDomicilio() {
        return empresasDomicilio;
    }

    public void setEmpresasDomicilio(ArrayList<EmpresaDomicilio> empresasDomicilio) {
        this.empresasDomicilio = empresasDomicilio;
    }
}
