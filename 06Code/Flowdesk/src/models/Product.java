package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Product {
    private String productId;
    private String name;
    private String description;
    private double price;
    private Category category;

    private static List<Product> listaProductos = new ArrayList<>();

    public Product(String productId, String name, String description, double price, Category category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public void register() {
        listaProductos.add(this);
    }

    public static List<Product> getAll() {
        return listaProductos;
    }

    public static void registrarDesdeConsola(Scanner scanner) {
        if (Category.getAll().isEmpty()) {
            System.out.println("Primero debe crear al menos una categoria.");
            return;
        }

        System.out.print("ID Producto: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripcion: ");
        String desc = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());

        System.out.println("Seleccione Categoria:");
        for (Category c : Category.getAll()) System.out.println(c.toString());
        System.out.print("ID Categoria: ");
        Category cat = Category.search(scanner.nextLine());

        if (cat != null) {
            Product p = new Product(id, nombre, desc, precio, cat); // <-- AGREGADO
            p.register();
            System.out.println("Producto registrado.");
        } else {
            System.out.println("Categoria no encontrada.");
        }
    }

    public static void listarDesdeConsola() {
        System.out.println("\n--- PRODUCTOS ---");
        if (listaProductos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            for (Product p : listaProductos) {
                System.out.println("- [" + p.getProductId() + "] " + p.getName() + " | Precio: $" + p.getPrice() + " | Cat: " + p.getCategory().getName());
            }
        }
    }
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public Category getCategory() { return category; }


    public boolean update(Product data) {
        return false;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
