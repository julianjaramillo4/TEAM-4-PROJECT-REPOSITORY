package reports;

import enums.OrderStatus;
import models.WorkOrder;
import java.util.List;
import java.util.Map;

public class Dashboard {
    private List<WorkOrder> orders;
    private Map<OrderStatus, Integer> quantityByStatus;

    public void showSummary() {
    }

    public List<WorkOrder> filterOrders(OrderStatus status) {
        return null;
    }

    public void showPendingOrders() {
    }

    /**
     * @return the orders
     */
    public List<WorkOrder> getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(List<WorkOrder> orders) {
        this.orders = orders;
    }

    /**
     * @return the quantityByStatus
     */
    public Map<OrderStatus, Integer> getQuantityByStatus() {
        return quantityByStatus;
    }

    /**
     * @param quantityByStatus the quantityByStatus to set
     */
    public void setQuantityByStatus(Map<OrderStatus, Integer> quantityByStatus) {
        this.quantityByStatus = quantityByStatus;
    }
}
