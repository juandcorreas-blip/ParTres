import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Aplicacion {

    static Scanner sc = new Scanner(System.in);
    static Restaurante restaurante = new Restaurante("Sabor & Tradición", "900123456-7", "Armenia, Quindío");

    public static void main(String[] args) {
        cargarDatosIniciales();
        int opcion;
        do {
            System.out.println("\n===== " + restaurante.getNombreComercial() + " =====");
            System.out.println("1. Registrar producto");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Consultar productos disponibles y su precio de venta");
            System.out.println("4. Consultar productos que no cumplen calidad");
            System.out.println("5. Registrar pedido");
            System.out.println("6. Gestionar domicilio de un pedido");
            System.out.println("7. Confirmar pedido");
            System.out.println("8. Calcular valor total de un pedido");
            System.out.println("9. Consultar pedidos de un cliente");
            System.out.println("10. Actualizar estado de un pedido");
            System.out.println("11. Consultar estado de un pedido");
            System.out.println("12. Reporte de ventas");
            System.out.println("0. Salir");
            opcion = leerEntero("Elija una opción: ");

            switch (opcion) {
                case 1:
                    registrarProducto();
                    break;
                case 2:
                    registrarCliente();
                    break;
                case 3:
                    consultarProductosDisponibles();
                    break;
                case 4:
                    consultarProductosRechazados();
                    break;
                case 5:
                    registrarPedido();
                    break;
                case 6:
                    gestionarDomicilio();
                    break;
                case 7:
                    confirmarPedido();
                    break;
                case 8:
                    calcularValorPedido();
                    break;
                case 9:
                    consultarPedidosCliente();
                    break;
                case 10:
                    actualizarEstadoPedido();
                    break;
                case 11:
                    consultarEstadoPedido();
                    break;
                case 12:
                    reporteVentas();
                    break;
                case 0:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    // ---------------- Lectura de datos ----------------

    static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        int numero = sc.nextInt();
        sc.nextLine();
        return numero;
    }

    static double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        double numero = sc.nextDouble();
        sc.nextLine();
        return numero;
    }

    static boolean leerSiNo(String mensaje) {
        System.out.print(mensaje + " (s/n): ");
        String respuesta = sc.nextLine();
        return respuesta.equalsIgnoreCase("s");
    }

    // ---------------- Opciones del menú ----------------

    static void registrarProducto() {
        System.out.println("1. Plato fuerte  2. Bebida  3. Postre");
        int categoria = leerEntero("Categoría: ");
        if (categoria < 1 || categoria > 3) {
            System.out.println("Categoría no válida.");
            return;
        }
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nombre: ");
        String descripcion = leerTexto("Descripción: ");
        double precioBase = leerDecimal("Precio base: ");
        int tiempo = leerEntero("Tiempo de preparación (min): ");

        Producto producto = null;
        if (categoria == 1) {
            double peso = leerDecimal("Peso (g): ");
            System.out.println("1. Asado  2. Frito  3. Horneado  4. A la plancha");
            int tipo = leerEntero("Tipo de preparación: ");
            TipoPreparacion preparacion = TipoPreparacion.ASADO;
            if (tipo == 2) {
                preparacion = TipoPreparacion.FRITO;
            } else if (tipo == 3) {
                preparacion = TipoPreparacion.HORNEADO;
            } else if (tipo == 4) {
                preparacion = TipoPreparacion.A_LA_PLANCHA;
            }
            boolean coccion = leerSiNo("¿Pasó el control de cocción?");
            producto = new PlatoFuerte(codigo, nombre, descripcion, precioBase, tiempo, peso, preparacion, coccion);
        } else if (categoria == 2) {
            double volumen = leerDecimal("Volumen (ml): ");
            double temperatura = leerDecimal("Temperatura de servicio (°C): ");
            TipoBebida tipoBebida = TipoBebida.CALIENTE;
            if (leerSiNo("¿Es una bebida fría?")) {
                tipoBebida = TipoBebida.FRIA;
            }
            boolean especial = leerSiNo("¿Requiere preparación especial?");
            producto = new Bebida(codigo, nombre, descripcion, precioBase, tiempo, volumen, temperatura, tipoBebida, especial);
        } else {
            double peso = leerDecimal("Peso (g): ");
            double temperatura = leerDecimal("Temperatura de almacenamiento (°C): ");
            boolean refrigeracion = leerSiNo("¿Requiere refrigeración?");
            producto = new Postre(codigo, nombre, descripcion, precioBase, tiempo, peso, temperatura, refrigeracion);
        }

        if (restaurante.registrarProducto(producto)) {
            System.out.println("Producto registrado. Precio de venta: $" + producto.calcularPrecioVenta());
        } else {
            System.out.println("Ya existe un producto con ese código.");
        }
    }

    static void registrarCliente() {
        String documento = leerTexto("Documento: ");
        String nombre = leerTexto("Nombre completo: ");
        String telefono = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo: ");
        String direccion = leerTexto("Dirección: ");
        Cliente cliente = new Cliente(documento, nombre, telefono, correo, direccion);
        if (restaurante.registrarCliente(cliente)) {
            System.out.println("Cliente registrado.");
        } else {
            System.out.println("Ya existe un cliente con ese documento.");
        }
    }

    static void consultarProductosDisponibles() {
        ArrayList<Producto> disponibles = restaurante.consultarProductosDisponibles();
        if (disponibles.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return;
        }
        for (Producto producto : disponibles) {
            System.out.println(producto);
            System.out.println("   Precio de venta: $" + producto.calcularPrecioVenta());
        }
    }

    static void consultarProductosRechazados() {
        ArrayList<Producto> rechazados = restaurante.consultarProductosRechazados();
        if (rechazados.isEmpty()) {
            System.out.println("Todos los productos cumplen las condiciones de calidad.");
            return;
        }
        for (Producto producto : rechazados) {
            System.out.println(producto);
        }
    }

    static void registrarPedido() {
        String codigo = leerTexto("Código del pedido: ");
        String documento = leerTexto("Documento del cliente: ");
        Cliente cliente = restaurante.buscarCliente(documento);
        if (cliente == null) {
            System.out.println("El cliente no está registrado.");
            return;
        }
        System.out.println("1. Consumo en el restaurante  2. Domicilio");
        int modalidadOpcion = leerEntero("Modalidad de entrega: ");
        ModalidadEntrega modalidad = ModalidadEntrega.CONSUMO_EN_RESTAURANTE;
        if (modalidadOpcion == 2) {
            modalidad = ModalidadEntrega.DOMICILIO;
        }

        Pedido pedido = new Pedido(codigo, LocalDate.now(), cliente, modalidad);
        if (!restaurante.registrarPedido(pedido)) {
            System.out.println("Ya existe un pedido con ese código.");
            return;
        }

        // Se agregan productos hasta que se escriba 0
        String codigoProducto = leerTexto("Código del producto (0 para terminar): ");
        while (!codigoProducto.equals("0")) {
            Producto producto = restaurante.buscarProducto(codigoProducto);
            if (producto == null) {
                System.out.println("Ese producto no está en el menú.");
            } else {
                int cantidad = leerEntero("Cantidad: ");
                pedido.agregarDetalle(producto, cantidad);
            }
            codigoProducto = leerTexto("Código del producto (0 para terminar): ");
        }
        System.out.println("Pedido registrado como pendiente.");
    }

    static void gestionarDomicilio() {
        Pedido pedido = restaurante.buscarPedido(leerTexto("Código del pedido: "));
        if (pedido == null) {
            System.out.println("El pedido no existe.");
            return;
        }
        if (pedido.getModalidad() != ModalidadEntrega.DOMICILIO) {
            System.out.println("Este pedido no es a domicilio.");
            return;
        }
        System.out.println("1. RápidoExpress  2. EntregaSegura");
        int opcion = leerEntero("Empresa: ");
        if (opcion < 1 || opcion > 2) {
            System.out.println("Empresa no válida.");
            return;
        }
        EmpresaDomicilio empresa = restaurante.getEmpresasDomicilio().get(opcion - 1);
        double distancia = leerDecimal("Distancia del recorrido (km): ");
        Domicilio domicilio = empresa.solicitarServicio(distancia);
        pedido.setDomicilio(domicilio);
        if (domicilio.isAceptado()) {
            System.out.println(empresa.getNombre() + " aceptó el domicilio. Costo: $" + domicilio.getCosto());
        } else {
            System.out.println(empresa.getNombre() + " no acepta esa distancia (máximo "
                    + empresa.getDistanciaMaxima() + " km).");
        }
    }

    static void confirmarPedido() {
        String codigo = leerTexto("Código del pedido: ");
        Pedido pedido = restaurante.buscarPedido(codigo);
        if (pedido == null) {
            System.out.println("El pedido no existe.");
            return;
        }
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            System.out.println("El pedido ya está en estado " + pedido.getEstado() + ".");
            return;
        }
        String resultado = restaurante.validarPedido(pedido);
        if (resultado.equals("OK")) {
            restaurante.confirmarPedido(codigo);
            System.out.println("Pedido confirmado.");
        } else {
            System.out.println("No se puede confirmar: " + resultado);
        }
    }

    static void calcularValorPedido() {
        Pedido pedido = restaurante.buscarPedido(leerTexto("Código del pedido: "));
        if (pedido == null) {
            System.out.println("El pedido no existe.");
            return;
        }
        System.out.println("Valor de los productos: $" + pedido.calcularValorProductos());
        System.out.println("Costo del domicilio: $" + pedido.calcularCostoDomicilio());
        System.out.println("Valor total: $" + pedido.calcularValorTotal());
    }

    static void consultarPedidosCliente() {
        String documento = leerTexto("Documento del cliente: ");
        if (restaurante.buscarCliente(documento) == null) {
            System.out.println("El cliente no está registrado.");
            return;
        }
        ArrayList<Pedido> pedidos = restaurante.consultarPedidosCliente(documento);
        if (pedidos.isEmpty()) {
            System.out.println("El cliente no tiene pedidos.");
            return;
        }
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }

    static void actualizarEstadoPedido() {
        String codigo = leerTexto("Código del pedido: ");
        System.out.println("1. Entregado  2. Cancelado");
        int opcion = leerEntero("Nuevo estado: ");
        EstadoPedido nuevo = EstadoPedido.ENTREGADO;
        if (opcion == 2) {
            nuevo = EstadoPedido.CANCELADO;
        }
        if (restaurante.actualizarEstadoPedido(codigo, nuevo)) {
            System.out.println("Estado actualizado a " + nuevo + ".");
        } else {
            System.out.println("No se pudo cambiar el estado (el pedido no existe o no cumple las condiciones).");
        }
    }

    static void consultarEstadoPedido() {
        Pedido pedido = restaurante.buscarPedido(leerTexto("Código del pedido: "));
        if (pedido == null) {
            System.out.println("El pedido no existe.");
        } else {
            System.out.println("El pedido " + pedido.getCodigo() + " está " + pedido.getEstado() + ".");
        }
    }

    static void reporteVentas() {
        System.out.println("Ingresos del restaurante: $" + restaurante.calcularIngresosRestaurante());
        System.out.println("Valores cobrados por domicilios: $" + restaurante.calcularIngresosDomicilios());

        Producto masVendido = restaurante.buscarProductoMasVendido();
        if (masVendido == null) {
            System.out.println("Todavía no hay ventas.");
            return;
        }
        System.out.println("Producto más vendido: " + masVendido.getNombre() + " ("
                + restaurante.calcularUnidadesVendidas(masVendido) + " unidades)");
        CategoriaProducto categoria = restaurante.buscarCategoriaMayorIngreso();
        System.out.println("Categoría con mayores ingresos: " + categoria + " ($"
                + restaurante.calcularIngresosCategoria(categoria) + ")");
    }

    // ---------------- Datos de ejemplo para probar el menú ----------------

    static void cargarDatosIniciales() {
        restaurante.registrarProducto(new PlatoFuerte("P01", "Bandeja paisa", "Frijoles, arroz, carne y chicharrón",
                30000, 35, 600, TipoPreparacion.A_LA_PLANCHA, true));
        restaurante.registrarProducto(new PlatoFuerte("P02", "Trucha frita", "Trucha con patacón y ensalada",
                25000, 25, 450, TipoPreparacion.FRITO, true));
        // Este plato no pasó el control de cocción, así que no cumple calidad
        restaurante.registrarProducto(new PlatoFuerte("P03", "Pollo asado", "Pollo con papas",
                22000, 40, 500, TipoPreparacion.ASADO, false));
        restaurante.registrarProducto(new Bebida("B01", "Limonada natural", "Limonada con hielo",
                6000, 5, 400, 5, TipoBebida.FRIA, false));
        restaurante.registrarProducto(new Bebida("B02", "Café especial", "Café de origen preparado en V60",
                5000, 8, 200, 70, TipoBebida.CALIENTE, true));
        // Fría pero servida a 20 °C, no cumple calidad
        restaurante.registrarProducto(new Bebida("B03", "Jugo de mora", "Jugo en agua",
                4500, 5, 350, 20, TipoBebida.FRIA, false));
        restaurante.registrarProducto(new Postre("D01", "Tres leches", "Torta de tres leches",
                8000, 5, 150, 4, true));
        restaurante.registrarProducto(new Postre("D02", "Brownie", "Brownie con nueces",
                7000, 5, 120, 22, false));

        restaurante.registrarCliente(new Cliente("1094111111", "Laura Gómez", "3101234567",
                "laura@correo.com", "Calle 14 # 12-30"));
        restaurante.registrarCliente(new Cliente("1094222222", "Carlos Ruiz", "3207654321",
                "carlos@correo.com", "Carrera 18 # 20-15"));
    }
}
