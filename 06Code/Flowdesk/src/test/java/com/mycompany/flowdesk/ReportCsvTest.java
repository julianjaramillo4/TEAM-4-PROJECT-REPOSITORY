package com.mycompany.flowdesk;

import org.junit.jupiter.api.Test;
import reports.CsvExporter;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportCsvTest {

    @Test
    void testEscapeCsvComas() {
        String entrada = "Cliente, Juan";
        String esperado = "\"Cliente, Juan\"";
        String resultado = CsvExporter.escapeCsvField(entrada);
        assertEquals(esperado, resultado, "Error en escape con comas");
    }

    @Test
    void testEscapeCsvComillas() {
        String entrada = "Pago \"Efectivo\"";
        String esperado = "\"Pago \"\"Efectivo\"\"\"";
        String resultado = CsvExporter.escapeCsvField(entrada);
        assertEquals(esperado, resultado, "Error en escape con comillas");
    }

    @Test
    void testEscapeCsvTextoNormal() {
        String entrada = "Orden123";
        String esperado = "Orden123";
        String resultado = CsvExporter.escapeCsvField(entrada);
        assertEquals(esperado, resultado, "Error en texto normal");
    }
}
