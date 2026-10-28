package reports;

import enums.OrderStatus;
import models.Payment;
import models.WorkOrder;
import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    
    private ArrayList<Integer> conteoPorEstado;
    private ArrayList<Double> ingresosPorMetodoPago;
    private ArrayList<WorkOrder> ordenesDelReporte;

    public Dashboard() {
        conteoPorEstado = new ArrayList<>();
        ingresosPorMetodoPago = new ArrayList<>();
        ordenesDelReporte = new ArrayList<>();
    }

    private void inicializarListas() {
        conteoPorEstado.clear();
        ingresosPorMetodoPago.clear();

        for (int i = 0; i < 5; i++) {
            conteoPorEstado.add(0); 
        }
        
        for (int i = 0; i < 3; i++) {
            ingresosPorMetodoPago.add(0.0);
        }
    }

    public void setOrders(List<WorkOrder> ordenes) {
        if (ordenes == null || ordenes.isEmpty()) {
            this.ordenesDelReporte = new ArrayList<>();
            return; 
        }
        
        this.ordenesDelReporte = new ArrayList<>(ordenes);
        realizarCalculos();
    }

    private void realizarCalculos() {
        inicializarListas(); 
        
        for (WorkOrder orden : ordenesDelReporte) {
            
            if (orden.getStatus() != null) {
                int estadoIndex = orden.getStatus().ordinal(); 
                
                if (estadoIndex >= 0 && estadoIndex < conteoPorEstado.size()) {
                    int conteoActual = conteoPorEstado.get(estadoIndex);
                    conteoPorEstado.set(estadoIndex, conteoActual + 1); 
                }
            }

            if (orden.getPayments() != null) {
                for (Payment pago : orden.getPayments()) {
                    if (pago.getPaymentMethod() != null) {
                        int pagoIndex = pago.getPaymentMethod().ordinal(); 
                        
                        if (pagoIndex >= 0 && pagoIndex < ingresosPorMetodoPago.size()) {
                            double ingresoActual = ingresosPorMetodoPago.get(pagoIndex);
                            ingresosPorMetodoPago.set(pagoIndex, ingresoActual + pago.getAmount()); 
                        }
                    }
                }
            }
        }
    }

    public void mostrarDashboard() {
        System.out.println("\n=============================================");
        System.out.println("       DASHBOARD DE ORDENES (US003)          ");
        System.out.println("=============================================");
        
        if (ordenesDelReporte.isEmpty()) {
            System.out.println(" No hay ordenes registradas en el sistema.   ");
            System.out.println("=============================================\n");
            return;
        }

        System.out.println("TOTAL DE ORDENES: " + ordenesDelReporte.size());
        System.out.println("---------------------------------------------");
        
        System.out.println("Ordenes en ESPERA (WAITING):        " + conteoPorEstado.get(0)); 
        System.out.println("Ordenes APROBADAS (APPROVED):       " + conteoPorEstado.get(1));
        System.out.println("Ordenes EN PROGRESO (IN_PROGRESS):  " + conteoPorEstado.get(2));
        System.out.println("Ordenes COMPLETADAS (COMPLETED):    " + conteoPorEstado.get(3));
        System.out.println("Ordenes ENTREGADAS (DELIVERED):     " + conteoPorEstado.get(4));
        
        System.out.println("\n--- INGRESOS REALES (PAGOS RECIBIDOS) ---");
        System.out.printf("Efectivo:      $%.2f\n", ingresosPorMetodoPago.get(0));
        System.out.printf("Tarjeta:       $%.2f\n", ingresosPorMetodoPago.get(1));
        System.out.printf("Transferencia: $%.2f\n", ingresosPorMetodoPago.get(2));
        
        System.out.println("=============================================\n");
    }
}