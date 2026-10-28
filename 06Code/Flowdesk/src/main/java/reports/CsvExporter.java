package reports;

import models.CashMovement;
import models.WorkOrder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvExporter {

    private static final String DIRECTORY = "reportes";

    private static void ensureDirectoryExists() {
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static String escapeCsvField(String field) {
        if (field == null) {
            return "";
        }
        boolean mustQuote =
                field.contains(",")
                        || field.contains("\"")
                        || field.contains("\n")
                        || field.contains("\r");
        if (mustQuote) {
            String escaped = field.replace("\"", "\"\"");
            return "\"" + escaped + "\"";
        }
        return field;
    }

    public static String exportOrdersToCsv(List<WorkOrder> ordenes) {
        ensureDirectoryExists();
        String timestamp =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = DIRECTORY + File.separator + "reporte_ordenes_" + timestamp + ".csv";

        try (PrintWriter writer =
                new PrintWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            writer.println("ID,Fecha,Cliente,Estado,Total,Pagado,Saldo");

            if (ordenes != null) {
                int contador = 1;
                for (WorkOrder orden : ordenes) {
                    String id = escapeCsvField("ORD-" + (contador++));
                    String fecha =
                            escapeCsvField(
                                    LocalDateTime.now()
                                            .format(
                                                    DateTimeFormatter.ofPattern(
                                                            "dd/MM/yyyy HH:mm")));
                    String cliente =
                            escapeCsvField(
                                    orden.getCustomer() != null
                                            ? orden.getCustomer().getName()
                                            : "N/A");
                    String estado =
                            escapeCsvField(
                                    orden.getStatus() != null ? orden.getStatus().name() : "");
                    double total = orden.getTotal();
                    double saldo = orden.getBalance();
                    double pagado = total - saldo;

                    writer.printf(
                            "%s,%s,%s,%s,%.2f,%.2f,%.2f\n",
                            id, fecha, cliente, estado, total, pagado, saldo);
                }
            }
            return fileName;
        } catch (IOException e) {
            System.out.println("Error al exportar ordenes a CSV: " + e.getMessage());
            return null;
        }
    }

    public static String exportMovementsToCsv(List<CashMovement> movimientos) {
        ensureDirectoryExists();
        String timestamp =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = DIRECTORY + File.separator + "reporte_movimientos_" + timestamp + ".csv";

        try (PrintWriter writer =
                new PrintWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            writer.println("ID,Fecha,Tipo,Motivo,metodoPago,Monto");

            if (movimientos != null) {
                int contador = 1;
                for (CashMovement mov : movimientos) {
                    String id = escapeCsvField("MOV-" + (contador++));
                    String fecha =
                            escapeCsvField(
                                    LocalDateTime.now()
                                            .format(
                                                    DateTimeFormatter.ofPattern(
                                                            "dd/MM/yyyy HH:mm")));
                    String tipo = escapeCsvField(mov.getType() != null ? mov.getType().name() : "");
                    String motivo = escapeCsvField(mov.getReason());
                    String metodoPago =
                            escapeCsvField(
                                    mov.getPaymentMethod() != null
                                            ? mov.getPaymentMethod().name()
                                            : "EFECTIVO");
                    double monto = mov.getAmount();

                    writer.printf(
                            "%s,%s,%s,%s,%s,%.2f\n", id, fecha, tipo, motivo, metodoPago, monto);
                }
            }
            return fileName;
        } catch (IOException e) {
            System.out.println("Error al exportar movimientos a CSV: " + e.getMessage());
            return null;
        }
    }
}
