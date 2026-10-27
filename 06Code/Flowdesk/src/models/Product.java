package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.ConsoleInput;
import utils.Validation;
import exceptions.ValidationException;

public class Product {
    private String productId;
    private String name;
    private String description;
    private double price;
    private Category category;

    private static List<Product> products = new ArrayList<>();

    public Product(String productId, String name, String description, double price, Category category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        validate();
    }

    public Product() { }

    public static void setAll(List<Product> newItems) {
        products = newItems;
    }

    public void register() {
        validate();
        for (Product existing : products) {
            if (Validation.sameIdentifier(existing.productId, productId)) {
                throw new ValidationException("Ya existe un producto con ese ID.");
            }
        }
        products.add(this);
    }

    void validate() {
        Validation.requireText(productId, "identificador del producto");
        Validation.requireText(name, "nombre del producto");
        Validation.requireText(description, "descripcion");
        Validation.requireNonNegativeAmount(price, "precio");
        Validation.requireValue(category, "categoria");
        category.validate();
    }

    public static List<Product> getAll() {
        return products;
    }

    public static void registerFromConsole(Scanner scanner) {
        if (Category.getAll().isEmpty()) {
            System.out.println("Primero debe crear al menos una categoria.");
            return;
        }

        String id = ConsoleInput.readRequiredText(scanner, "ID Producto: ");
        String name = ConsoleInput.readRequiredText(scanner, "Nombre: ");
        String description = ConsoleInput.readRequiredText(scanner, "Descripcion: ");
        double price = ConsoleInput.readAmount(scanner, "Precio: ");

        System.out.println("Seleccione Categoria:");
        for (Category c : Category.getAll()) System.out.println(c.toString());
        Category category = Category.search(ConsoleInput.readRequiredText(scanner, "ID Categoria: "));

        if (category != null) {
            Product p = new Product(id, name, description, price, category);
            p.register();
            System.out.println("Producto registrado.");
        } else {
            System.out.println("Categoria no encontrada.");
        }
    }

    public static void listFromConsole() {
        System.out.println("\n--- PRODUCTOS ---");
        if (products.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            for (Product p : products) {
                System.out.println("- [" + p.getProductId() + "] " + p.getName() + " | Precio: $" + p.getPrice() + " | Cat: " + p.getCategory().getName());
            }
        }
    }

    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public Category getCategory() { return category; }
}
