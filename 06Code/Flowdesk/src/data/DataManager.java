package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import models.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String DATA_DIRECTORY = "datos_flowdesk/";

    public static void init() {
        File dir = new File(DATA_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static <T> void saveList(List<T> items, String fileName) {
        init();
        try (Writer writer = new FileWriter(DATA_DIRECTORY + fileName + ".json", StandardCharsets.UTF_8)) {
            gson.toJson(items, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar " + fileName + ". Verifique el archivo y los permisos de acceso.");
        }
    }

    public static <T> List<T> loadList(String fileName, Type listType) {
        init();
        File file = new File(DATA_DIRECTORY + fileName + ".json");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            List<T> items = gson.fromJson(reader, listType);
            return items != null ? items : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al cargar " + fileName + ". Verifique el archivo y los permisos de acceso.");
            return new ArrayList<>();
        }
    }

    public static void saveAll() {
        saveList(Customer.getAll(), "clientes");
        saveList(Category.getAll(), "categorias");
        saveList(Product.getAll(), "productos");
        saveList(Service.getAll(), "servicios");
        saveList(WorkOrder.getAll(), "ordenes");
        saveList(User.getAll(), "usuarios");
    }

    public static void loadAll() {
        Type customersType = new TypeToken<ArrayList<Customer>>(){}.getType();
        Customer.setAll(loadList("clientes", customersType));

        Type categoriesType = new TypeToken<ArrayList<Category>>(){}.getType();
        Category.setAll(loadList("categorias", categoriesType));

        Type productsType = new TypeToken<ArrayList<Product>>(){}.getType();
        Product.setAll(loadList("productos", productsType));

        Type servicesType = new TypeToken<ArrayList<Service>>(){}.getType();
        Service.setAll(loadList("servicios", servicesType));

        Type ordersType = new TypeToken<ArrayList<WorkOrder>>(){}.getType();
        WorkOrder.setAll(loadList("ordenes", ordersType));

        Type usersType = new TypeToken<ArrayList<User>>(){}.getType();
        User.setAll(loadList("usuarios", usersType));
    }

    public static void saveCashRegister(CashRegister cashRegister) {
        init();
        try (Writer writer = new FileWriter(DATA_DIRECTORY + "caja.json", StandardCharsets.UTF_8)) {
            gson.toJson(cashRegister, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar caja. Verifique el archivo y los permisos de acceso.");
        }
    }

    public static CashRegister loadCashRegister() {
        init();
        File file = new File(DATA_DIRECTORY + "caja.json");
        if (!file.exists()) {
            return new CashRegister();
        }
        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            CashRegister cashRegister = gson.fromJson(reader, CashRegister.class);
            return cashRegister != null ? cashRegister : new CashRegister();
        } catch (IOException e) {
            System.out.println("Error al cargar caja. Verifique el archivo y los permisos de acceso.");
            return new CashRegister();
        }
    }
}
