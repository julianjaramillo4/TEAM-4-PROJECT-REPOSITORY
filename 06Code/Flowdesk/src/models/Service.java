package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Service {
    private String serviceId;
    private String name;
    private String description;
    private double price;
    private Category category;

    private static List<Service> listaServicios = new ArrayList<>();

    public Service(String serviceId, String name, String description, double price, Category category) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
    
    public Service() { } 

    public static void setAll(List<Service> nuevaLista) {
        listaServicios = nuevaLista;
    }    

    public void register() {
        listaServicios.add(this);
    }

    public static List<Service> getAll() {
        return listaServicios;
    }

    public static void registrarDesdeConsola(Scanner scanner) {
        if (Category.getAll().isEmpty()) {
            System.out.println("Primero debe crear al menos una categoria.");
            return;
        }

        System.out.print("ID Servicio: ");
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
            Service s = new Service(id, nombre, desc, precio, cat);
            s.register();
            System.out.println("Servicio registrado.");
        } else {
            System.out.println("Categoria no encontrada.");
        }
    }
    
    public static void listarDesdeConsola() {
        System.out.println("\n--- SERVICIOS ---");
        if (listaServicios.isEmpty()) {
            System.out.println("No hay servicios registrados.");
        } else {
            for (Service s : listaServicios) {
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