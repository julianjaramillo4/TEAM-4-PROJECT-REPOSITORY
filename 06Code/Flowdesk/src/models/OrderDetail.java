package models;

public class OrderDetail {
    private String detailId;
    private String itemName;
    private int quantity;
    private String description;
    private double unitPrice;
    
    // Relaciones (según diagrama)
    private Product product; 
    private Service service; 

    public OrderDetail(String detailId, String itemName, int quantity, String description, double unitPrice) {
        this.detailId = detailId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public double calculateSubtotal() {
        return quantity * unitPrice;
    }

    // Getters y Setters
    public String getDetailId() { return detailId; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public String getDescription() { return description; }
    public double getUnitPrice() { return unitPrice; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Service getService() { return service; }
    public void setService(Service service) { this.service = service; }
}