package reports;

import models.CashRegister;
import java.util.Scanner;

public class Report {

    private String title;
    private String content;

    public Report() {
        this.title = "Reporte General Flowdesk";
        this.content = "";
    }

    public Report(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static void generateFromConsole(Scanner scanner, CashRegister cashRegister) {
        System.out.println("\n--- GENERACION DE REPORTES ---");
        if (cashRegister == null) {
            System.out.println("No hay datos de caja disponibles para generar el reporte.");
            return;
        }

        ReportGenerator.generarReporteGeneral(
            models.WorkOrder.getAll(), 
            cashRegister.getMovements()
        );
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "=== " + title + " ===\n" + content;
    }
}