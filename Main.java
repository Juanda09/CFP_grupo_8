import java.io.PrintWriter;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase Main
 * 
 * Procesa los archivos generados en la Entrega 1:
 * - productos.csv
 * - vendedores.csv
 * - Archivos individuales de ventas por cada vendedor
 *
 * Y genera los reportes solicitados en la Entrega 2:
 * - reporte_vendedores.csv
 * - reporte_productos.csv
 * 
 * Proyecto: Generación y clasificación de datos
 * Entrega 2 - Grupo 8
 */
public class Main {

    // 📂 Configuración general
    private static String INPUT_DIR = "archivos_generados";
    private static final String ENCODING = "UTF-8";

    // Estructuras de datos
    private static final Map<String, Producto> productos = new HashMap<>();
    private static final Map<String, Vendedor> vendedores = new HashMap<>();

    /**
     * Clase interna que representa un Producto
     */
    static class Producto {
        String id;
        String nombre;
        double precio;
        int cantidadVendida = 0;

        Producto(String id, String nombre, double precio) {
            this.id = id;
            this.nombre = nombre;
            this.precio = precio;
        }
    }

    /**
     * Clase interna que representa un Vendedor
     */
    static class Vendedor {
        String tipoDoc;
        String numDoc;
        String nombre;
        String apellido;
        double totalRecaudado = 0.0;

        Vendedor(String tipoDoc, String numDoc, String nombre, String apellido) {
            this.tipoDoc = tipoDoc;
            this.numDoc = numDoc;
            this.nombre = nombre;
            this.apellido = apellido;
        }
    }

    /**
     * Método principal.
     */
    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                INPUT_DIR = args[0];
            }

            System.out.println("🚀 Iniciando procesamiento en: " + Paths.get(INPUT_DIR).toAbsolutePath());

            cargarProductos();
            cargarVendedores();
            procesarArchivosVentas();

            generarReporteVendedores();
            generarReporteProductos();

            mostrarResumen();

        } catch (Exception e) {
            System.err.println("❌ ERROR crítico en procesamiento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 📌 Cargar productos desde productos.csv
    public static void cargarProductos() throws Exception {
        Path filePath = Paths.get(INPUT_DIR, "productos.csv");

        if (!Files.exists(filePath)) {
            throw new Exception("No se encontró productos.csv en " + INPUT_DIR);
        }

        for (String linea : Files.readAllLines(filePath)) {
            String[] d = linea.split(";");
            productos.put(d[0], new Producto(d[0], d[1], Double.parseDouble(d[2])));
        }

        System.out.println("🛒 productos.csv cargado → " + productos.size() + " productos.");
    }

    // 📌 Cargar vendedores desde vendedores.csv
    public static void cargarVendedores() throws Exception {
        Path filePath = Paths.get(INPUT_DIR, "vendedores.csv");

        if (!Files.exists(filePath)) {
            throw new Exception("No se encontró vendedores.csv en " + INPUT_DIR);
        }

        for (String linea : Files.readAllLines(filePath)) {
            String[] d = linea.split(";");
            vendedores.put(d[1], new Vendedor(d[0], d[1], d[2], d[3]));
        }

        System.out.println("👥 vendedores.csv cargado → " + vendedores.size() + " vendedores.");
    }

    // 📌 Procesar archivos de ventas
    public static void procesarArchivosVentas() throws Exception {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(INPUT_DIR), "vendedor_*.csv")) {
            for (Path file : stream) {
                List<String> lineas = Files.readAllLines(file);

                if (lineas.isEmpty()) continue;

                String[] cabecera = lineas.get(0).split(";");
                String idVendedor = cabecera[1];

                Vendedor vendedor = vendedores.get(idVendedor);
                if (vendedor == null) {
                    System.err.println("⚠️ Vendedor no encontrado para archivo: " + file.getFileName());
                    continue;
                }

                for (int i = 1; i < lineas.size(); i++) {
                    String[] datos = lineas.get(i).split(";");
                    if (datos.length < 2) continue;

                    Producto producto = productos.get(datos[0]);
                    int cantidad = Integer.parseInt(datos[1]);

                    if (producto != null && cantidad > 0) {
                        vendedor.totalRecaudado += producto.precio * cantidad;
                        producto.cantidadVendida += cantidad;
                    } else {
                        System.err.println("⚠️ Producto no válido en archivo: " + file.getFileName());
                    }
                }

                System.out.println("📄 " + file.getFileName() + " procesado.");
            }
        }
    }

    // 📌 Generar reporte de vendedores
    public static void generarReporteVendedores() throws Exception {
        Path filePath = Paths.get(INPUT_DIR, "reporte_vendedores.csv");

        try (PrintWriter writer = new PrintWriter(filePath.toFile(), ENCODING)) {
            writer.println("Nombre;TotalRecaudado");

            vendedores.values().stream()
                .sorted((v1, v2) -> Double.compare(v2.totalRecaudado, v1.totalRecaudado))
                .forEach(v -> writer.printf("%s %s;%.2f%n", v.nombre, v.apellido, v.totalRecaudado));
        }

        System.out.println("📊 reporte_vendedores.csv generado.");
    }

    // 📌 Generar reporte de productos
    public static void generarReporteProductos() throws Exception {
        Path filePath = Paths.get(INPUT_DIR, "reporte_productos.csv");

        try (PrintWriter writer = new PrintWriter(filePath.toFile(), ENCODING)) {
            writer.println("Producto;CantidadVendida");

            productos.values().stream()
                .sorted((p1, p2) -> Integer.compare(p2.cantidadVendida, p1.cantidadVendida))
                .forEach(p -> writer.printf("%s;%d%n", p.nombre, p.cantidadVendida));
        }

        System.out.println("📊 reporte_productos.csv generado.");
    }

    // 📌 Mostrar resumen extendido en consola
    public static void mostrarResumen() {
        System.out.println("\n📊 Resumen de reportes:");
        System.out.println("- Productos procesados: " + productos.size());
        System.out.println("- Vendedores procesados: " + vendedores.size());
        System.out.println("- Archivos de reportes generados: 2");

        // Mejor vendedor
        Vendedor mejorVendedor = vendedores.values().stream()
            .max(Comparator.comparingDouble(v -> v.totalRecaudado))
            .orElse(null);

        // Producto más vendido
        Producto productoTop = productos.values().stream()
            .max(Comparator.comparingInt(p -> p.cantidadVendida))
            .orElse(null);

        if (mejorVendedor != null) {
            System.out.printf("🏆 Mejor vendedor: %s %s con $%.2f%n",
                mejorVendedor.nombre, mejorVendedor.apellido, mejorVendedor.totalRecaudado);
        }
        if (productoTop != null) {
            System.out.printf("📦 Producto más vendido: %s con %d unidades%n",
                productoTop.nombre, productoTop.cantidadVendida);
        }

        System.out.println("\n✅ Procesamiento finalizado con éxito.");
    }
}
