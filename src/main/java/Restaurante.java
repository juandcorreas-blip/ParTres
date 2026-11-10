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

    // ---------------- Productos ----------------

    public Producto buscarProducto(String codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        return null;
    }

    // No deja registrar dos productos con el mismo código
    public boolean registrarProducto(Producto producto) {
        if (buscarProducto(producto.getCodigo()) != null) {
            return false;
        }
        productos.add(producto);
        return true;
    }

    // Productos del menú que cumplen las condiciones de calidad
    public ArrayList<Producto> consultarProductosDisponibles() {
        ArrayList<Producto> disponibles = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.cumpleCondicionesCalidad()) {
                disponibles.add(producto);
            }
        }
        return disponibles;
    }

    // Productos del menú que no cumplen las condiciones de calidad
    public ArrayList<Producto> consultarProductosRechazados() {
        ArrayList<Producto> rechazados = new ArrayList<>();
        for (Producto producto : productos) {
            if (!producto.cumpleCondicionesCalidad()) {
                rechazados.add(producto);
            }
        }
        return rechazados;
    }

    // ---------------- Clientes ----------------

    public Cliente buscarCliente(String documento) {
        for (Cliente cliente : clientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null;
    }

    // No deja registrar dos clientes con el mismo documento
    public boolean registrarCliente(Cliente cliente) {
        if (buscarCliente(cliente.getDocumento()) != null) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    // ---------------- Pedidos ----------------

    public Pedido buscarPedido(String codigo) {
        for (Pedido pedido : pedidos) {
            if (pedido.getCodigo().equals(codigo)) {
                return pedido;
            }
        }
        return null;
    }

    // Registra el pedido como pendiente, sin confirmar todavía
    public boolean registrarPedido(Pedido pedido) {
        if (buscarPedido(pedido.getCodigo()) != null) {
            return false;
        }
        if (buscarCliente(pedido.getCliente().getDocumento()) == null) {
            return false;
        }
        pedidos.add(pedido);
        return true;
    }

    // Revisa las condiciones para confirmar. Devuelve "OK" o el motivo por el que no se puede
    public String validarPedido(Pedido pedido) {
        if (buscarCliente(pedido.getCliente().getDocumento()) == null) {
            return "El cliente no está registrado.";
        }
        if (pedido.getDetalles().isEmpty()) {
            return "El pedido no tiene productos.";
        }
        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto producto = detalle.getProducto();
            if (buscarProducto(producto.getCodigo()) == null) {
                return "El producto " + producto.getNombre() + " no pertenece al menú.";
            }
            if (!detalle.cantidadValida()) {
                return "La cantidad de " + producto.getNombre() + " debe ser mayor que cero.";
            }
            if (!producto.cumpleCondicionesCalidad()) {
                return "El producto " + producto.getNombre() + " no cumple las condiciones de calidad.";
            }
        }
        if (pedido.getModalidad() == ModalidadEntrega.DOMICILIO) {
            if (pedido.getDomicilio() == null || !pedido.getDomicilio().isAceptado()) {
                return "Ninguna empresa ha aceptado el domicilio.";
            }
        }
        return "OK";
    }

    // Confirma el pedido y lo guarda en el historial del cliente
    public boolean confirmarPedido(String codigo) {
        Pedido pedido = buscarPedido(codigo);
        if (pedido == null) {
            return false;
        }
        // Evita confirmaciones repetidas
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            return false;
        }
        if (!validarPedido(pedido).equals("OK")) {
            return false;
        }
        pedido.setEstado(EstadoPedido.CONFIRMADO);
        pedido.getCliente().agregarPedido(pedido);
        return true;
    }

    // Pedidos de un cliente, de cualquier estado
    public ArrayList<Pedido> consultarPedidosCliente(String documento) {
        ArrayList<Pedido> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().getDocumento().equals(documento)) {
                resultado.add(pedido);
            }
        }
        return resultado;
    }

    // Cambia el estado respetando las reglas: solo se entrega un pedido confirmado
    // y no se puede cancelar uno ya entregado o cancelado
    public boolean actualizarEstadoPedido(String codigo, EstadoPedido nuevoEstado) {
        Pedido pedido = buscarPedido(codigo);
        if (pedido == null) {
            return false;
        }
        if (nuevoEstado == EstadoPedido.ENTREGADO) {
            if (pedido.getEstado() != EstadoPedido.CONFIRMADO) {
                return false;
            }
            pedido.setEstado(EstadoPedido.ENTREGADO);
            return true;
        }
        if (nuevoEstado == EstadoPedido.CANCELADO) {
            if (pedido.getEstado() == EstadoPedido.ENTREGADO || pedido.getEstado() == EstadoPedido.CANCELADO) {
                return false;
            }
            pedido.setEstado(EstadoPedido.CANCELADO);
            return true;
        }
        // Confirmar se hace con confirmarPedido y no se puede volver a pendiente
        return false;
    }

    // ---------------- Reportes ----------------

    // Un pedido cuenta como venta si está confirmado o entregado (los cancelados y pendientes no)
    private boolean cuentaComoVenta(Pedido pedido) {
        return pedido.getEstado() == EstadoPedido.CONFIRMADO || pedido.getEstado() == EstadoPedido.ENTREGADO;
    }

    // Ingresos propios del restaurante, sin domicilios
    public double calcularIngresosRestaurante() {
        double total = 0;
        for (Pedido pedido : pedidos) {
            if (cuentaComoVenta(pedido)) {
                total = total + pedido.calcularValorProductos();
            }
        }
        return total;
    }

    // Lo cobrado por domicilios, aparte de los ingresos del restaurante
    public double calcularIngresosDomicilios() {
        double total = 0;
        for (Pedido pedido : pedidos) {
            if (cuentaComoVenta(pedido)) {
                total = total + pedido.calcularCostoDomicilio();
            }
        }
        return total;
    }

    public int calcularUnidadesVendidas(Producto producto) {
        int unidades = 0;
        for (Pedido pedido : pedidos) {
            if (cuentaComoVenta(pedido)) {
                for (DetallePedido detalle : pedido.getDetalles()) {
                    if (detalle.getProducto().getCodigo().equals(producto.getCodigo())) {
                        unidades = unidades + detalle.getCantidad();
                    }
                }
            }
        }
        return unidades;
    }

    // Devuelve null si todavía no hay ventas
    public Producto buscarProductoMasVendido() {
        Producto masVendido = null;
        int mayor = 0;
        for (Producto producto : productos) {
            int unidades = calcularUnidadesVendidas(producto);
            if (unidades > mayor) {
                mayor = unidades;
                masVendido = producto;
            }
        }
        return masVendido;
    }

    public double calcularIngresosCategoria(CategoriaProducto categoria) {
        double total = 0;
        for (Pedido pedido : pedidos) {
            if (cuentaComoVenta(pedido)) {
                for (DetallePedido detalle : pedido.getDetalles()) {
                    if (detalle.getProducto().getCategoria() == categoria) {
                        total = total + detalle.calcularSubtotal();
                    }
                }
            }
        }
        return total;
    }

    // Devuelve null si todavía no hay ventas
    public CategoriaProducto buscarCategoriaMayorIngreso() {
        CategoriaProducto mejor = null;
        double mayor = 0;
        CategoriaProducto[] categorias = CategoriaProducto.values();
        for (int i = 0; i < categorias.length; i++) {
            double ingresos = calcularIngresosCategoria(categorias[i]);
            if (ingresos > mayor) {
                mayor = ingresos;
                mejor = categorias[i];
            }
        }
        return mejor;
    }

}
