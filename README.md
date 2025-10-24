# Taller 1 - Integración mediante transferencia de archivos con Apache Camel

## Descripción

Este proyecto implementa una solución de integración de sistemas utilizando Apache Camel para automatizar la transferencia y transformación de archivos CSV entre el sistema de ventas y el sistema de inventario de la empresa TiendaSol.

## Objetivo

Leer archivos CSV desde una carpeta de entrada, transformar su contenido a mayúsculas y moverlos a través de diferentes etapas (output → archived), aplicando el patrón clásico de integración por transferencia de archivos.

## Tecnologías utilizadas

- Java 17
- Apache Camel 4.4.0
- Maven 3.9.11

## Estructura del proyecto
```
camel-file-transfer/
├── data/
│   ├── input/       # Archivos de entrada (CSV)
│   ├── output/      # Archivos procesados y transformados
│   ├── archived/    # Archivos archivados
│   └── logs/        # Archivos con errores
├── src/main/java/com/tiendasol/integration/
│   ├── MainApp.java
│   └── MyRouteBuilder.java  # Definición de las rutas de integración
└── pom.xml
```

## Flujo de integración

1. **Monitoreo:** Camel monitorea la carpeta `data/input` cada 5 segundos
2. **Filtrado:** Solo procesa archivos con extensión `.csv`
3. **Lectura:** Lee el contenido del archivo
4. **Transformación:** Convierte todo el texto a MAYÚSCULAS
5. **Output:** Guarda el archivo transformado en `data/output`
6. **Archivado:** Después de 5-10 segundos, mueve el archivo a `data/archived`
7. **Logging:** Registra fecha, hora y estado de cada operación

## Instalación y ejecución

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+

### Pasos

1. **Clonar el repositorio:**
```bash
   git clone https://github.com/TU_USUARIO/camel-file-transfer-taller.git
   cd camel-file-transfer-taller
```

2. **Crear las carpetas necesarias:**
```bash
   mkdir -p data/input data/output data/archived data/logs
```

3. **Crear un archivo CSV de prueba en `data/input/ventas.csv`:**
```csv
   id,producto,cantidad,precio
   1,Monitor,2,150
   2,Teclado,5,25
   3,Mouse,3,15
```

4. **Ejecutar el proyecto:**
```bash
   mvn clean compile exec:java
```

## Ejemplo de uso

**Archivo original (`input/ventas.csv`):**
```
id,producto,cantidad,precio
1,Monitor,2,150
```

**Archivo transformado (`output/ventas.csv` → `archived/ventas.csv`):**
```
ID,PRODUCTO,CANTIDAD,PRECIO
1,MONITOR,2,150
```

## Características implementadas

- ✅ Lectura de archivos CSV
- ✅ Transformación de contenido a mayúsculas
- ✅ Filtrado por extensión de archivo
- ✅ Movimiento automático entre carpetas
- ✅ Logging con timestamps
- ✅ Manejo de errores
- ✅ Procesamiento asíncrono

## Patrón de integración

Este proyecto implementa el patrón **File Transfer**, uno de los patrones clásicos de Enterprise Integration Patterns (EIP), que permite la comunicación entre sistemas mediante el intercambio de archivos.

