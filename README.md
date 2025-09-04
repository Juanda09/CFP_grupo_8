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

### 2. Compilar y ejecutar en Eclipse
- Importa el repositorio como **Java Project** en Eclipse.  
- Ubica la clase `GenerateInfoFiles.java`.  
- Haz clic derecho > **Run As → Java Application**.  

### 3. Ejecutar desde consola (opcional)
Si prefieres usar consola:
```bash
javac src/GenerateInfoFiles.java
java -cp src GenerateInfoFiles
```

También puedes indicar una **ruta personalizada** para guardar los archivos:
```bash
java -cp src GenerateInfoFiles "C:/MisDatos/Proyecto/"
```

---

## 📂 Archivos generados (ejemplo)

### `productos.csv`
```csv
1;Laptop;2500000.5
2;Mouse;80000.0
3;Teclado;150000.99
4;Monitor;950000.0
```

### `vendedores.csv`
```csv
CC;123456789;Carlos;Gomez
TI;987654321;Ana;Perez
CE;456789123;Juan;Martinez
```

### `vendedor_123456789.csv`
```csv
CC;123456789
1;2;
3;1;
2;5;
```

---

## 📊 Resumen de ejecución
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
- **Grupo 8:**
- **Integrantes:** 
  - Juan David Huertas Zapata 
  -  
- **Correo:** h0774762@gmail.com  
- **Institución:** Politécnico Grancolombiano  
