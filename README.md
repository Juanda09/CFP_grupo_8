# Proyecto: Generación y clasificación de datos - Entrega Final (1, 2 y 3)

## 📌 Descripción
Este proyecto corresponde a la **Entrega final** de la asignatura de Especialización en Programación.  
El objetivo es simular un sistema de ventas usando **archivos planos** como base de datos, a través de tres fases:

- **Entrega 1:** Se generan los archivos de entrada (`productos.csv`, `vendedores.csv` y `vendedor_<id>.csv`).  
- **Entrega 2:** Se procesan los archivos de entrada para obtener reportes (`reporte_vendedores.csv` y `reporte_productos.csv`).  
- **Entrega 3:** Entrega final bien documentada, con las dos clases principales (`GenerateInfoFiles` y `Main`), mensajes de éxito/error, sin interacción con el usuario y con el archivo `conclusion.txt`.

---

## ⚙️ Ejecución paso a paso

### 1. Clonar el repositorio
```bash
git clone https://github.com/Juanda09/CFP_grupo_8.git
cd CFP_grupo_8
```

### 2. Compilar las clases
Desde la carpeta raíz del proyecto:
```bash
javac src/GenerateInfoFiles.java src/Main.java
```

---

### 3. Ejecutar **Entrega 1** → generación de datos
La clase `GenerateInfoFiles` crea los archivos de entrada en la carpeta **archivos_generados**.

```bash
java -cp src GenerateInfoFiles
```

🔎 **Pasos realizados automáticamente:**
1. Se crea la carpeta `archivos_generados` (si no existe).  
2. Se genera el catálogo de productos en `productos.csv`.  
3. Se genera la lista de vendedores en `vendedores.csv`.  
4. Por cada vendedor, se genera un archivo individual `vendedor_<id>.csv` con sus ventas.  
5. Al final, se muestra en consola un resumen de lo creado o un error en caso de fallo.  

📌 **Ejemplo de salida en consola:**
```
📂 Iniciando generación en: /ruta/archivos_generados
🛒 productos.csv → 4 productos generados.
👥 vendedores.csv → 3 vendedores generados.
📄 vendedor_123456789.csv → 3 ventas.
📄 vendedor_987654321.csv → 2 ventas.

📊 Resumen de generación:
- productos.csv → 4 registros
- vendedores.csv → 3 registros
- Archivos de vendedores → 2 archivos
✅ Generación finalizada con éxito.
```

---

### 4. Ejecutar **Entrega 2** → procesamiento de reportes
La clase `Main` lee los archivos creados en la Entrega 1 y genera los reportes de salida.

```bash
java -cp src Main
```

🔎 **Pasos realizados automáticamente:**
1. Se cargan los productos desde `productos.csv`.  
2. Se cargan los vendedores desde `vendedores.csv`.  
3. Se recorren todos los archivos `vendedor_<id>.csv`.  
   - Se calcula el total recaudado por cada vendedor.  
   - Se acumula la cantidad total vendida por cada producto.  
4. Se generan dos reportes con **encabezados claros**:  
   - `reporte_vendedores.csv` (ordenado por ventas totales descendentes).  
   - `reporte_productos.csv` (ordenado por cantidad vendida descendente).  
5. Se muestra un **resumen extendido en consola** con el mejor vendedor y el producto más vendido.  

📌 **Ejemplo de salida en consola:**
```
🚀 Iniciando procesamiento en: /ruta/archivos_generados
🛒 productos.csv cargado → 4 productos.
👥 vendedores.csv cargado → 3 vendedores.
📄 vendedor_123456789.csv procesado.
📄 vendedor_987654321.csv procesado.
📊 reporte_vendedores.csv generado.
📊 reporte_productos.csv generado.

📊 Resumen de reportes:
- Productos procesados: 4
- Vendedores procesados: 3
- Archivos de reportes generados: 2
🏆 Mejor vendedor: Ana Perez con $5150000.00
📦 Producto más vendido: Mouse con 20 unidades

✅ Procesamiento finalizado con éxito.
```

---

## 📂 Archivos generados

### Entrega 1
- **productos.csv** → catálogo de productos.  
- **vendedores.csv** → lista de vendedores.  
- **vendedor_<id>.csv** → ventas individuales por vendedor.  

### Entrega 2
- **reporte_vendedores.csv** → vendedores con el total recaudado, ordenados descendentemente.  
  ```csv
  Nombre;TotalRecaudado
  Carlos Gomez;5150001.99
  Ana Perez;2850000.00
  ```
- **reporte_productos.csv** → productos con la cantidad total vendida, ordenados descendentemente.  
  ```csv
  Producto;CantidadVendida
  Mouse;6
  Laptop;2
  ```

### Entrega 3
- Entrega final con documentación consolidada.  
- Archivo `conclusion.txt` con aprendizajes, aplicaciones profesionales y dificultades encontradas.  

---

## 👨‍💻 Autor
- **Grupo 8**  
- **Integrantes:**  
  - Juan David Huertas Zapata  
  - Andres Felipe Chacon Cifuentes  
  - Miguel Angel Mendoza Niño  
  - Nestor Antonio Romero Guerrero  
  - Erica Varela Quintero  
- **Institución:** Politécnico Grancolombiano  
