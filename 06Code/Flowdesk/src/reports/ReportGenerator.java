package reports;

import models.CashMovement;
import models.WorkOrder;

import java.util.List;

public class ReportGenerator {

    public static void generarReporteGeneral(List<WorkOrder> ordenes, List<CashMovement> movimientos) {
        String fileOrdenes = CsvExporter.exportOrdersToCsv(ordenes);
        String fileMovimientos = CsvExporter.exportMovementsToCsv(movimientos);

        System.out.println("\n==================================================");
        System.out.println("          EXPORTACION A CSV COMPLETADA            ");
        System.out.println("==================================================");
        if (fileOrdenes != null) {
            System.out.println("-> Reporte de Ordenes guardado en: " + fileOrdenes);
        }
        if (fileMovimientos != null) {
            System.out.println("-> Reporte de Movimientos guardado en: " + fileMovimientos);
        }
        System.out.println("==================================================\n");
    }

    public static void exportarOrdenesCSV(List<WorkOrder> ordenes) {
        String archivo = CsvExporter.exportOrdersToCsv(ordenes);
        if (archivo != null) {
            System.out.println("-> Archivo de ordenes exportado en: " + archivo);
        }
    }

    public static void exportarMovimientosCSV(List<CashMovement> movimientos) {
        String archivo = CsvExporter.exportMovementsToCsv(movimientos);
        if (archivo != null) {
            System.out.println("-> Archivo de movimientos exportado en: " + archivo);
        }
    }
}