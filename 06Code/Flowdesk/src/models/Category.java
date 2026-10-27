package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.ConsoleInput;

public class Category {
    private String categoryId;
    private String name;
    private String description;

    private static List<Category> categories = new ArrayList<>();

    public Category(String categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public Category() { }

    public static void setAll(List<Category> newItems) {
        categories = newItems;
    }

    public void create() {
        categories.add(this);
    }

    public static List<Category> getAll() {
        return categories;
    }

    public static Category search(String id) {
        for (Category c : categories) {
            if (c.getCategoryId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    public static void registerFromConsole(Scanner scanner) {
        String id = ConsoleInput.readRequiredText(scanner, "ID Categoria: ");
        String name = ConsoleInput.readRequiredText(scanner, "Nombre Categoria: ");
        String description = ConsoleInput.readRequiredText(scanner, "Descripcion: ");

        Category newCategory = new Category(id, name, description);
        newCategory.create();
        System.out.println("Categoria creada exitosamente.");
    }

    public String getCategoryId() { return categoryId; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "[" + categoryId + "] " + name;
    }
}
