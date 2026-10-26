package reports;

import enums.OrderStatus;
import enums.ReportType;
import models.CashRegister;
import models.WorkOrder;
import utils.Date;
import java.util.Scanner;

public class Report {
    private String reportId;
    private Date startDate;
    private Date endDate;
    private ReportType reportType;

    public Report(String reportId, Date startDate, Date endDate, ReportType reportType) {
        this.reportId = reportId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reportType = reportType;
    }

    public static void generarDesdeConsola(Scanner scanner, CashRegister caja) {
        System.out.println("\n--- GENERAR REPORTE ---");
        System.out.println("Seleccione el tipo de reporte:");
        System.out.println("1. Diario");
        System.out.println("2. Semanal");
        System.out.println("3. Mensual");
        System.out.print("Opcion: ");

        int opcion = Integer.parseInt(scanner.nextLine());
        ReportType tipo = null;

        switch (opcion) {
            case 1 -> tipo = ReportType.DAILY;
            case 2 -> tipo = ReportType.WEEKLY;
            case 3 -> tipo = ReportType.MONTHLY;
            default -> {
                System.out.println("Opcion no valida.");
                return;
            }
        }

        System.out.println("\n=============================================");
        System.out.println("        REPORTE " + tipo + " DE FLOWDESK      ");
        System.out.println("=============================================");
        System.out.println("Fecha de emision: 24/10/2026 (Simulada)");
        System.out.println("---------------------------------------------");

        // 1. Resumen de Ordenes
        int totalOrdenes = WorkOrder.getAll().size();
        int completadas = 0;
        int pendientes = 0;
        double dineroPorCobrar = 0;

        for (WorkOrder wo : WorkOrder.getAll()) {
            if (wo.getStatus() == OrderStatus.COMPLETED || wo.getStatus() == OrderStatus.DELIVERED) {
                completadas++;
            } else {
                pendientes++;
            }
            dineroPorCobrar += wo.getBalance();
        }

        System.out.println("[*] ESTADO DE ORDENES:");
        System.out.println("    - Total de ordenes: " + totalOrdenes);
        System.out.println("    - Ordenes terminadas: " + completadas);
        System.out.println("    - Ordenes pendientes: " + pendientes);
        System.out.println("    - Dinero por cobrar (Saldos): $" + dineroPorCobrar);

        // 2. Resumen Financiero (Caja)
        System.out.println("\n[*] RESUMEN FINANCIERO (CAJA):");
        System.out.println("    - Ingresos totales: $" + caja.calculateIncome());
        System.out.println("    - Egresos totales:  $" + caja.calculateExpenses());
        System.out.println("    - Balance neto:     $" + caja.calculateBalance());

        System.out.println("=============================================");
        System.out.println("             FIN DEL REPORTE                 ");
        System.out.println("=============================================\n");
    }

    // Getters
    public String getReportId() { return reportId; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public ReportType getReportType() { return reportType; }
}