package models;

public class OrderDetail {
    private String detailId;
    private String itemName;
    private int quantity;
    private String description;
    private double unitPrice;
    private Product product;
    private Service service;

    public OrderDetail(String detailId, String itemName, int quantity, String description, double unitPrice) {
        this.detailId = detailId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public void addDetail() {
    }

    public void updateQuantity(int quantity) {
    }

    public double calculateSubtotal() {
        return quantity * unitPrice;
    }

    /**
     * @return the detailId
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * @param detailId the detailId to set
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the unitPrice
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the service
     */
    public Service getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(Service service) {
        this.service = service;
    }
}
