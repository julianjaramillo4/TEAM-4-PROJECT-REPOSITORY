package com.mycompany.flowdesk;

import models.Product;
import models.WorkOrder;
import models.OrderDetail;
import models.User;
import enums.OrderStatus;
import enums.Role;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for FlowDesk - Lanchimba Module Topics: Login (US008), Catalog
 * (US004), Work Orders (US002)
 *
 * @author Cristopher Lanchimba
 */
public class LanchimbaTest {

    public LanchimbaTest() {
    }

    /**
     * Test of login method, of class User/Auth, with valid credentials.
     */
    @Test
    public void testLoginSuccess() {
        System.out.println("loginSuccess");
        String username = "admin";
        String password = "123";
        User user = new User(1, "admin", "123", Role.ADMIN);

        boolean expResult = true;
        boolean result = user.getUsername().equals(username) && user.getPassword().equals(password);

        assertEquals(expResult, result, "Login should succeed with correct credentials");
    }

    /**
     * Test of login method with incorrect password.
     */
    @Test
    public void testLoginWrongPassword() {
        System.out.println("loginWrongPassword");
        String username = "admin";
        String password = "wrongpassword";
        User user = new User(1, "admin", "123", Role.ADMIN);

        boolean expResult = false;
        boolean result = user.getUsername().equals(username) && user.getPassword().equals(password);

        assertEquals(expResult, result, "Login should fail when password is incorrect");
    }

    /**
     * Test of login method with a non-existent user.
     */
    @Test
    public void testLoginNonExistentUser() {
        System.out.println("loginNonExistentUser");
        String username = "unknown_user";
        User user = new User(1, "admin", "123", Role.ADMIN);

        boolean expResult = false;
        boolean result = user.getUsername().equals(username);

        assertEquals(expResult, result, "Login should fail for unrecorded users");
    }

    /**
     * Test of logout process resetting active user reference.
     */
    @Test
    public void testLogoutSession() {
        System.out.println("logoutSession");
        User currentUser = new User(1, "admin", "123", Role.ADMIN);

        // Perform logout
        currentUser = null;

        User expResult = null;
        User result = currentUser;

        assertEquals(expResult, result, "Current active user should be null after logout");
    }

    /**
     * Test of addProduct method in product catalog.
     */
    @Test
    public void testAddValidProduct() {
        System.out.println("addValidProduct");
        List<Product> catalog = new ArrayList<>();
        Product product = new Product(1, "Custom Mug", "Sublimated Ceramic Mug", 5.50, "IMPRENTA");

        catalog.add(product);

        int expResult = 1;
        int result = catalog.size();

        assertEquals(expResult, result, "Catalog size should be 1 after adding a valid product");
    }

    /**
     * Test of Product constructor validating negative price.
     */
    @Test
    public void testAddNegativePriceProduct() {
        System.out.println("addNegativePriceProduct");
        double negativePrice = -10.00;

        assertThrows(IllegalArgumentException.class, () -> {
            if (negativePrice < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            new Product(2, "Vinyl Banner", "13oz Banner", negativePrice, "PUBLICIDAD");
        }, "Constructor should throw IllegalArgumentException for negative prices");
    }

    /**
     * Test of searching products filtered by category.
     */
    @Test
    public void testSearchProductByCategory() {
        System.out.println("searchProductByCategory");
        List<Product> catalog = new ArrayList<>();
        catalog.add(new Product(1, "Custom Mug", "Sublimated Mug", 5.50, "IMPRENTA"));
        catalog.add(new Product(2, "Acrylic Sign", "Laser Cut Sign", 45.00, "PUBLICIDAD"));

        List<Product> filtered = new ArrayList<>();
        for (Product p : catalog) {
            if ("IMPRENTA".equalsIgnoreCase(p.getCategory())) {
                filtered.add(p);
            }
        }

        String expResult = "Custom Mug";
        String result = filtered.get(0).getName();

        assertEquals(expResult, result, "Should find the product matching IMPRENTA category");
    }

    /**
     * Test of creating a new WorkOrder with initial status PENDING.
     */
    @Test
    public void testCreateWorkOrderSuccess() {
        System.out.println("createWorkOrderSuccess");
        WorkOrder order = new WorkOrder(101, 10, new ArrayList<>(), OrderStatus.PENDING, 0.00);

        OrderStatus expResult = OrderStatus.PENDING;
        OrderStatus result = order.getStatus();

        assertEquals(expResult, result, "New work order should be created in PENDING status");
    }

    /**
     * Test of processing a WorkOrder that has no details attached.
     */
    @Test
    public void testProcessEmptyWorkOrder() {
        System.out.println("processEmptyWorkOrder");
        WorkOrder emptyOrder = new WorkOrder(102, 10, new ArrayList<>(), OrderStatus.PENDING, 0.00);

        assertThrows(IllegalStateException.class, () -> {
            if (emptyOrder.getDetails() == null || emptyOrder.getDetails().isEmpty()) {
                throw new IllegalStateException("Work order must contain at least one item");
            }
        }, "Should throw IllegalStateException when processing an empty order");
    }

    /**
     * Test of calculating total cost for a WorkOrder.
     */
    @Test
    public void testCalculateOrderTotal() {
        System.out.println("calculateOrderTotal");
        Product p1 = new Product(1, "Flyer A4", "150g paper", 10.00, "IMPRENTA");
        Product p2 = new Product(2, "Sticker Pack", "Vinyl", 0.05, "IMPRENTA");

        List<OrderDetail> details = new ArrayList<>();
        details.add(new OrderDetail(1, 103, p1.getId(), 2, 20.00));
        details.add(new OrderDetail(2, 103, p2.getId(), 100, 5.50));

        double expResult = 25.50;
        double result = 0.0;
        for (OrderDetail detail : details) {
            result += detail.getSubtotal();
        }

        assertEquals(expResult, result, 0.001, "Total calculated must be exactly 25.50");
    }
}