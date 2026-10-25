package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Category {
    private String categoryId;
    private String name;
    private String description;

    private static List<Category> listaCategorias = new ArrayList<>();

    public Category(String categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public void create() {
        listaCategorias.add(this);
    }

    public static List<Category> getAll() {
        return listaCategorias;
    }

    public static Category search(String id) {
        for (Category c : listaCategorias) {
            if (c.getCategoryId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    public static void registrarDesdeConsola(Scanner scanner) {
        System.out.print("ID Categoria: ");
        String id = scanner.nextLine();
        System.out.print("Nombre Categoria: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripcion: ");
        String desc = scanner.nextLine();

        Category nueva = new Category(id, nombre, desc);
        nueva.create();
        System.out.println("Categoria creada exitosamente.");
    }

    public String getCategoryId() { return categoryId; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "[" + categoryId + "] " + name;
    }


    public void updateCategory() {
    }

    public List<Product> listProducts() {
        return null;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
