package reports;

import models.WorkOrder;
import models.CashMovement;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator {

    public static void generarReporteGeneral(List<WorkOrder> ordenes, List<CashMovement> movimientos) {
        StringBuilder reporte = new StringBuilder();
        
        // 1. Mostrar fecha real
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaReal = ahora.format(formatter);

        reporte.append("==================================================\n");
        reporte.append("           REPORTE GENERAL CONSOLIDADO            \n");
        reporte.append("==================================================\n");
        reporte.append("Fecha de generacion: ").append(fechaReal).append("\n\n");

        // 2. Resumen de órdenes
        reporte.append("--- RESUMEN DE ORDENES ---\n");
        if (ordenes == null || ordenes.isEmpty()) {
            reporte.append("No hay ordenes de trabajo registradas.\n");
        } else {
            for (WorkOrder orden : ordenes) {
                // Se apoya en el toString() de WorkOrder para mostrar estados y saldos
                reporte.append(orden.toString()).append("\n"); 
            }
        }
        reporte.append("\n");

        // 3. Resumen de movimientos de caja
        reporte.append("--- RESUMEN DE MOVIMIENTOS DE CAJA ---\n");
        if (movimientos == null || movimientos.isEmpty()) {
            reporte.append("No hay movimientos de caja registrados.\n");
        } else {
            for (CashMovement movimiento : movimientos) {
                // Se apoya en el toString() de CashMovement para los detalles
                reporte.append(movimiento.toString()).append("\n"); 
            }
        }
        reporte.append("\n");
        
        reporte.append("==================================================\n");
        reporte.append("                 FIN DEL REPORTE                  \n");
        reporte.append("==================================================\n");

        // AQUÍ ESTABA EL ERROR: Faltaba el (); y cerrar el proceso correctamente
        String contenidoFinal = reporte.toString();

        // 4. Exportar reporte general consolidado a un archivo de texto
        try (FileWriter writer = new FileWriter("ReporteGeneral.txt")) {
            writer.write(contenidoFinal);
            System.out.println("\n[EXITO] Reporte generado correctamente en el archivo: ReporteGeneral.txt");
        } catch (IOException e) {
            System.out.println("\n[ERROR] No se pudo guardar el reporte: " + e.getMessage());
        }
    }
}