import java.io.PrintWriter;
import java.nio.file.*;
import java.util.Random;

/**
 * Clase GenerateInfoFiles
 * 
 * Genera archivos planos de prueba para el proyecto:
 * - productos.csv
 * - vendedores.csv
 * - Archivos individuales de ventas por cada vendedor
 *
 * Proyecto: Generación y clasificación de datos
 * Entrega 1 - Grupo 8
 * 
 * Integrantes:
 * - Juan David Huertas Zapata
 * - Andres Felipe Chacon Cifuentes
 * - Miguel Angel Mendoza Niño
 * - Nestor Antonio Romero Guerrero
 * - Erica Varela Quintero
 */
public class GenerateInfoFiles {

    // 📂 Configuración general
    private static String OUTPUT_DIR = "archivos_generados";
    private static final int DEFAULT_NUM_VENDEDORES = 3;
    private static final int MIN_VENTAS = 2;
    private static final int MAX_VENTAS = 5;
    private static final long SEED = System.currentTimeMillis(); 
    private static final String ENCODING = "UTF-8";

    // Datos de ejemplo
    private static final String[] NOMBRES = {"Carlos", "Ana", "Luis", "Maria", "Juan"};
    private static final String[] APELLIDOS = {"Gomez", "Perez", "Rodriguez", "Martinez"};
    private static final String[] TIPOS_DOC = {"CC", "CE", "TI"};
    private static final String[] PRODUCTOS_NOMBRES = {"Laptop", "Mouse", "Teclado", "Monitor"};
    private static final double[] PRODUCTOS_PRECIOS = {2500000.50, 80000.00, 150000.99, 950000.00};

    // Contadores para resumen
    private static int totalProductos = 0;
    private static int totalVendedores = 0;
    private static int totalArchivosVendedores = 0;

    /**
     * Método principal.
     * Genera todos los archivos de prueba requeridos para el proyecto.
     */
    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                OUTPUT_DIR = args[0]; // Permitir directorio por parámetro
            }

            Path carpeta = Paths.get(OUTPUT_DIR);
            if (!Files.exists(carpeta)) {
                Files.createDirectories(carpeta);
            }

            System.out.println("📂 Iniciando generación en: " + carpeta.toAbsolutePath());

            createProductsFile(PRODUCTOS_NOMBRES.length);
            createSalesManInfoFile(DEFAULT_NUM_VENDEDORES);

            showSummary();

        } catch (Exception e) {
            System.err.println("❌ ERROR crítico en generación de archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Genera un archivo de productos con ID, nombre y precio.
     * @param productsCount número de productos a generar
     */
    public static void createProductsFile(int productsCount) throws Exception {
        Path filePath = Paths.get(OUTPUT_DIR, "productos.csv");

        try (PrintWriter writer = new PrintWriter(filePath.toFile(), ENCODING)) {
            for (int i = 0; i < productsCount; i++) {
                writer.println((i + 1) + ";" + PRODUCTOS_NOMBRES[i] + ";" + PRODUCTOS_PRECIOS[i]);
            }
        }

        totalProductos = productsCount;
        System.out.println("🛒 productos.csv → " + productsCount + " productos generados.");
    }

    /**
     * Genera un archivo con información de vendedores y sus respectivos archivos de ventas.
     * @param salesmanCount número de vendedores a generar
     */
    public static void createSalesManInfoFile(int salesmanCount) throws Exception {
        Random rand = new Random(SEED);
        Path filePath = Paths.get(OUTPUT_DIR, "vendedores.csv");

        try (PrintWriter writer = new PrintWriter(filePath.toFile(), ENCODING)) {
            for (int i = 0; i < salesmanCount; i++) {
                long id = 100000000L + rand.nextInt(900000000);
                String nombre = NOMBRES[rand.nextInt(NOMBRES.length)];
                String apellido = APELLIDOS[rand.nextInt(APELLIDOS.length)];
                String tipoDoc = TIPOS_DOC[rand.nextInt(TIPOS_DOC.length)];

                writer.println(tipoDoc + ";" + id + ";" + nombre + ";" + apellido);

                int ventas = rand.nextInt(MAX_VENTAS - MIN_VENTAS + 1) + MIN_VENTAS;
                createSalesMenFile(ventas, tipoDoc, id, rand);
            }
        }

        totalVendedores = salesmanCount;
        System.out.println("👥 vendedores.csv → " + salesmanCount + " vendedores generados.");
    }

    /**
     * Genera el archivo de ventas para un vendedor específico.
     * @param randomSalesCount número aleatorio de ventas
     * @param tipoDoc tipo de documento del vendedor
     * @param id número de documento del vendedor
     * @param rand generador aleatorio
     */
    public static void createSalesMenFile(int randomSalesCount, String tipoDoc, long id, Random rand) throws Exception {
        Path filePath = Paths.get(OUTPUT_DIR, "vendedor_" + id + ".csv");

        try (PrintWriter writer = new PrintWriter(filePath.toFile(), ENCODING)) {
            writer.println(tipoDoc + ";" + id);
            for (int i = 0; i < randomSalesCount; i++) {
                int prodId = rand.nextInt(PRODUCTOS_NOMBRES.length) + 1;
                int cantidad = rand.nextInt(10) + 1;
                writer.println(prodId + ";" + cantidad + ";");
            }
        }

        totalArchivosVendedores++;
        System.out.println("📄 vendedor_" + id + ".csv → " + randomSalesCount + " ventas.");
    }

    /**
     * Muestra un resumen final de la generación de archivos.
     */
    public static void showSummary() {
        System.out.println("\n📊 Resumen de generación:");
        System.out.println("- productos.csv → " + totalProductos + " registros");
        System.out.println("- vendedores.csv → " + totalVendedores + " registros");
        System.out.println("- Archivos de vendedores → " + totalArchivosVendedores + " archivos");
        System.out.println("\n✅ Generación finalizada con éxito.");
    }
}
