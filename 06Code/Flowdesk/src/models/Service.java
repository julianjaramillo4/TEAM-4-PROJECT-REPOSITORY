package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.ConsoleInput;

public class Service {
    private String serviceId;
    private String name;
    private String description;
    private double price;
    private Category category;

    private static List<Service> services = new ArrayList<>();

    public Service(String serviceId, String name, String description, double price, Category category) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Service() { }

    public static void setAll(List<Service> newItems) {
        services = newItems;
    }

    public void register() {
        services.add(this);
    }

    public static List<Service> getAll() {
        return services;
    }

    public static void registerFromConsole(Scanner scanner) {
        if (Category.getAll().isEmpty()) {
            System.out.println("Primero debe crear al menos una categoria.");
            return;
        }

        String id = ConsoleInput.readRequiredText(scanner, "ID Servicio: ");
        String name = ConsoleInput.readRequiredText(scanner, "Nombre: ");
        String description = ConsoleInput.readRequiredText(scanner, "Descripcion: ");
        double price = ConsoleInput.readAmount(scanner, "Precio: ");

        System.out.println("Seleccione Categoria:");
        for (Category c : Category.getAll()) System.out.println(c.toString());
        Category category = Category.search(ConsoleInput.readRequiredText(scanner, "ID Categoria: "));

        if (category != null) {
            Service s = new Service(id, name, description, price, category);
            s.register();
            System.out.println("Servicio registrado.");
        } else {
            System.out.println("Categoria no encontrada.");
        }
    }

    public static void listFromConsole() {
        System.out.println("\n--- SERVICIOS ---");
        if (services.isEmpty()) {
            System.out.println("No hay servicios registrados.");
        } else {
            for (Service s : services) {
                System.out.println("- [" + s.getServiceId() + "] " + s.getName() + " | Cat: " + s.getCategory().getName() + " | $" + s.getPrice());
            }
        }
    }

    public String getServiceId() { return serviceId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public Category getCategory() { return category; }
}
