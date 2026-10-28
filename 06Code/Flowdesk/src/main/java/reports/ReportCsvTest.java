package reports;

public class ReportCsvTest {

    public static void main(String[] args) {
        System.out.println("--- EJECUTANDO PRUEBAS UNITARIAS DE CSV ---");

        testEscapeCsvComas();
        testEscapeCsvComillas();
        testEscapeCsvTextoNormal();

        System.out.println("--- TODAS LAS PRUEBAS PASARON EXITOSAMENTE ---");
    }

    private static void testEscapeCsvComas() {
        String entrada = "Cliente, Juan";
        String esperado = "\"Cliente, Juan\"";
        String resultado = CsvExporter.escapeCsvField(entrada);
        assert resultado.equals(esperado) : "Error en escape con comas. Obtenido: " + resultado;
        System.out.println("[OK] Prueba de escape con comas aprobada.");
    }

    private static void testEscapeCsvComillas() {
        String entrada = "Pago \"Efectivo\"";
        String esperado = "\"Pago \"\"Efectivo\"\"\"";
        String resultado = CsvExporter.escapeCsvField(entrada);
        assert resultado.equals(esperado) : "Error en escape con comillas. Obtenido: " + resultado;
        System.out.println("[OK] Prueba de escape con comillas aprobada.");
    }

    private static void testEscapeCsvTextoNormal() {
        String entrada = "Orden123";
        String esperado = "Orden123";
        String resultado = CsvExporter.escapeCsvField(entrada);
        assert resultado.equals(esperado) : "Error en texto normal. Obtenido: " + resultado;
        System.out.println("[OK] Prueba de texto normal aprobada.");
    }
}