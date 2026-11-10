import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido {

    private String codigo;
    private LocalDate fecha;
    private Cliente cliente;
    private ModalidadEntrega modalidad;
    private EstadoPedido estado;
    private ArrayList<DetallePedido> detalles;
    private Domicilio domicilio;

    public Pedido(String codigo, LocalDate fecha, Cliente cliente, ModalidadEntrega modalidad) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.modalidad = modalidad;
        this.estado = EstadoPedido.PENDIENTE;
        this.detalles = new ArrayList<>();
        this.domicilio = null;
    }
    public void agregarDetalle(Producto producto, int cantidad) {
        detalles.add(new DetallePedido(producto, cantidad));
    }

    // Valor de los productos, sin domicilio
    public double calcularValorProductos() {
        double total = 0;
        for (DetallePedido detalle : detalles) {
            total = total + detalle.calcularSubtotal();
        }
        return total;
    }

    // Solo cuenta si el pedido es a domicilio y el servicio fue aceptado
    public double calcularCostoDomicilio() {
        if (modalidad == ModalidadEntrega.DOMICILIO && domicilio != null && domicilio.isAceptado()) {
            return domicilio.getCosto();
        }
        return 0;
    }

    public double calcularValorTotal() {
        return calcularValorProductos() + calcularCostoDomicilio();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ModalidadEntrega getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadEntrega modalidad) {
        this.modalidad = modalidad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public ArrayList<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
    @Override
    public String toString() {
        String texto = "Pedido " + codigo + " | Fecha: " + fecha + " | Cliente: " + cliente.getNombreCompleto()
                + " | Modalidad: " + modalidad + " | Estado: " + estado;
        for (DetallePedido detalle : detalles) {
            texto = texto + "\n   - " + detalle;
        }
        if (domicilio != null) {
            texto = texto + "\n   Domicilio: " + domicilio;
        }
        texto = texto + "\n   Valor total: $" + calcularValorTotal();
        return texto;
    }
}