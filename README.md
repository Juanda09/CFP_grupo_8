# Proyecto: Generación y clasificación de datos - Entrega 1

## 📌 Descripción
Este proyecto corresponde a la **Entrega 1** de la asignatura de Especialización.  
El objetivo es generar archivos planos que sirvan como **entrada** para el programa principal que organizará los datos en entregas posteriores.  

La clase principal es **`GenerateInfoFiles`**, que al ejecutarse crea los siguientes archivos:

- `productos.csv` → Contiene la lista de productos disponibles con su ID, nombre y precio.  
- `vendedores.csv` → Contiene la información de los vendedores (tipo de documento, ID, nombre y apellido).  
- `vendedor_<id>.csv` → Contiene las ventas realizadas por cada vendedor de forma independiente.  

Todos los archivos se guardan en la carpeta **`archivos_generados`** por defecto, o en una ruta personalizada indicada al ejecutar el programa.

---

## ⚙️ Ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/Juanda09/CFP_grupo_8.git
cd CFP_grupo_8
```

### 2. Compilar el código
Desde la carpeta del proyecto:
```bash
javac src/GenerateInfoFiles.java
```

### 3. Ejecutar el programa
```bash
java -cp src GenerateInfoFiles
```

También puedes indicar una **ruta personalizada** para guardar los archivos:
```bash
java -cp src GenerateInfoFiles "C:/MisDatos/Proyecto/"
```

---

## 📂 Archivos generados

### 1. productos.csv
Catálogo de productos con ID, nombre y precio.
```csv
1;Laptop;2500000.5
2;Mouse;80000.0
3;Teclado;150000.99
4;Monitor;950000.0
```

### 2. vendedores.csv
Lista de vendedores con su tipo de documento, número, nombre y apellido.
```csv
CC;123456789;Carlos;Gomez
TI;987654321;Ana;Perez
CE;456789123;Juan;Martinez
```

### 3. vendedor_<id>.csv
Archivo individual por cada vendedor con sus ventas.

Ejemplo:
```csv
CC;123456789
1;2;
3;1;
2;5;
```

Esto significa que el vendedor con documento **CC 123456789** vendió:
- 2 unidades del producto con ID 1 (Laptop)
- 1 unidad del producto con ID 3 (Teclado)
- 5 unidades del producto con ID 2 (Mouse)

---

## 📊 Salida en consola

Al finalizar, el programa muestra un resumen en consola como el siguiente:
```
📂 Iniciando generación en: /ruta/archivos_generados
🛒 productos.csv → 4 productos generados.
👥 vendedores.csv → 3 vendedores generados.
📄 vendedor_123456789.csv → 3 ventas.
📄 vendedor_987654321.csv → 2 ventas.
📄 vendedor_456789123.csv → 5 ventas.

📊 Resumen de generación:
- productos.csv → 4 registros
- vendedores.csv → 3 registros
- Archivos de vendedores → 3 archivos
✅ ¡Archivos generados exitosamente!
```

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
