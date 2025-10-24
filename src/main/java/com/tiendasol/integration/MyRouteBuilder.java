package com.tiendasol.integration;

import org.apache.camel.builder.RouteBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        // Configurar manejo de errores
        errorHandler(defaultErrorHandler()
            .maximumRedeliveries(0)
            .logStackTrace(true));
        
        // Ruta principal: leer, transformar y escribir archivos
        // FILTRO: Solo archivos .csv
        from("file:data/input?delete=true&initialDelay=2000&delay=5000&include=.*\\.csv")
            .routeId("FileTransferRoute")
            .log("Procesando archivo: ${file:name}")
            
            // Filtro adicional en la ruta
            .filter(header("CamelFileName").endsWith(".csv"))
            
            .log("Archivo CSV valido, continuando procesamiento...")
            
            // Convertir archivo a String
            .convertBodyTo(String.class)
            
            .log("Contenido original:\n${body}")
            
            // Transformación: convertir a MAYÚSCULAS
            .process(exchange -> {
                String contenido = exchange.getIn().getBody(String.class);
                if (contenido != null) {
                    String contenidoMayusculas = contenido.toUpperCase();
                    exchange.getIn().setBody(contenidoMayusculas);
                    
                    // Log con fecha y hora
                    String fechaHora = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    log.info("Procesado en: " + fechaHora);
                    log.info("Transformacion exitosa");
                } else {
                    log.error("El contenido es null");
                }
            })
            
            .log("Contenido transformado:\n${body}")
            
            // Escribir el archivo transformado en output
            .to("file:data/output")
            
            .log("Archivo procesado y guardado en output/");
        
        // Ruta secundaria: mover archivos procesados de output a archived
        from("file:data/output?move=../archived&initialDelay=5000&delay=10000")
            .routeId("ArchiveRoute")
            .log("Archivando archivo: ${file:name}")
            .log("Archivo movido a carpeta archived/");
        
        log.info("Ruta de integracion iniciada - Monitoreando carpeta input/");
        log.info("Ruta de archivado iniciada - Monitoreando carpeta output/");
    }
}