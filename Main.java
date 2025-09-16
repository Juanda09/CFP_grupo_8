import java.io.PrintWriter;
import java.nio.file.*;
import java.util.*;

/**
 * Clase Main
 * 
 * Procesa los archivos generados en la Entrega 1 por GenerateInfoFiles:
 * - productos.csv
 * - vendedores.csv
 * - Archivos individuales de ventas por cada vendedor
 *
 * Y genera los reportes solicitados en la Entrega 2:
 * - reporte_vendedores.csv → total recaudado por cada vendedor (descendente)
 * - reporte_productos.csv → productos ordenados por cantidad vendida (descendente)
 * 
 * Proyecto: Generación y clasificación de datos
 * Entrega 2 - Grupo 8
 * 
 * Integrantes:
 * - Juan David Huertas Zapata
 * - Andres Felipe Chacon Cifuentes
 * - Miguel Angel Mendoza Niño
 * - Nestor Antonio Romero Guerrero
 * - Erica Varela Quintero
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
     * Procesa los archivos de entrada y genera los reportes solicitados.
     */
    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                INPUT_DIR = args[0]; // Permitir directorio personalizado
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

    /**
     * Carga los productos desde productos.csv
     */
    public static void cargarProductos() throws Exception {
        Path filePath = Paths.get(INPUT_DIR, "productos.csv");

        for (String linea : Files.readAllLines(filePath)) {
            String[] d = linea.split(";");
            productos.put(d[0], new Producto(d[0], d[1], Double.parseDouble(d[2])));
        }

        System.out.println("🛒 productos.csv cargado → " + productos.size() + " productos.");
    }

    /**
     * Carga los vendedores desde vendedores.csv
     */
    public static void cargarVendedores() throws Exception {
        Path filePath = Paths.get(INPUT_DIR, "vendedores.csv");

        for (String linea : Files.readAllLines(filePath)) {
            String[] d = linea.split(";");
            vendedores.put(d[1], new Vendedor(d[0], d[1], d[2], d[3]));
        }

        System.out.println("👥 vendedores.csv cargado → " + vendedores.size() + " vendedores.");
    }

    /**
     * Procesa todos los archivos de ventas de cada vendedor
     */
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
                    }
                }

                System.out.println("📄 " + file.getFileName() + " procesado.");
            }
        }
    }

    /**
     * Genera el reporte de vendedores ordenado por total recaudado
     */
    public static void generarReporteVendedores() throws Exception {
        Path filePath = Paths.get(INPUT_DIR, "reporte_vendedores.csv");

        try (PrintWriter writer = new PrintWriter(filePath.toFile(), ENCODING)) {
            vendedores.values().stream()
                .sorted((v1, v2) -> Double.compare(v2.totalRecaudado, v1.totalRecaudado))
                .forEach(v -> writer.println(v.nombre + " " + v.apellido + ";" + String.format("%.2f", v.totalRecaudado)));
        }

        System.out.println("📊 reporte_vendedores.csv generado.");
    }

    /**
     * Genera el reporte de productos ordenado por cantidad vendida
     */
    public static void generarReporteProductos() throws Exception {
        Path filePath = Paths.get(INPUT_DIR, "reporte_productos.csv");

        try (PrintWriter writer = new PrintWriter(filePath.toFile(), ENCODING)) {
            productos.values().stream()
                .sorted((p1, p2) -> Integer.compare(p2.cantidadVendida, p1.cantidadVendida))
                .forEach(p -> writer.println(p.nombre + ";" + p.cantidadVendida));
        }

        System.out.println("📊 reporte_productos.csv generado.");
    }

    /**
     * Muestra un resumen final en consola
     */
    public static void mostrarResumen() {
        System.out.println("\n📊 Resumen de reportes:");
        System.out.println("- Productos procesados: " + productos.size());
        System.out.println("- Vendedores procesados: " + vendedores.size());
        System.out.println("- Archivos de reportes generados: 2");
        System.out.println("\n✅ Procesamiento finalizado con éxito.");
    }
}
