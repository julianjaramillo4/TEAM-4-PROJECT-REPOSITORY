package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import models.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String CARPETA = "datos_flowdesk/";

    public static void init() {
        File dir = new File(CARPETA);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static <T> void guardarLista(List<T> lista, String nombreArchivo) {
        init();
        try (Writer writer = new FileWriter(CARPETA + nombreArchivo + ".json")) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar " + nombreArchivo + ": " + e.getMessage());
        }
    }

    public static <T> List<T> cargarLista(String nombreArchivo, Type tipoLista) {
        init();
        File archivo = new File(CARPETA + nombreArchivo + ".json");
        if (!archivo.exists()) {
            return new ArrayList<>(); // Retorna lista vacía si es la primera vez
        }
        try (Reader reader = new FileReader(archivo)) {
            List<T> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al cargar " + nombreArchivo + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void guardarTodo() {
        guardarLista(Customer.getAll(), "clientes");
        guardarLista(Category.getAll(), "categorias");
        guardarLista(Product.getAll(), "productos");
        guardarLista(Service.getAll(), "servicios");
        guardarLista(WorkOrder.getAll(), "ordenes");
        guardarLista(User.getAll(), "usuarios");
    }

    public static void cargarTodo() {
        Type tipoClientes = new TypeToken<ArrayList<Customer>>(){}.getType();
        Customer.setAll(cargarLista("clientes", tipoClientes));

        Type tipoCategorias = new TypeToken<ArrayList<Category>>(){}.getType();
        Category.setAll(cargarLista("categorias", tipoCategorias));

        Type tipoProductos = new TypeToken<ArrayList<Product>>(){}.getType();
        Product.setAll(cargarLista("productos", tipoProductos));

        Type tipoServicios = new TypeToken<ArrayList<Service>>(){}.getType();
        Service.setAll(cargarLista("servicios", tipoServicios));

        Type tipoOrdenes = new TypeToken<ArrayList<WorkOrder>>(){}.getType();
        WorkOrder.setAll(cargarLista("ordenes", tipoOrdenes));

        Type tipoUsuarios = new TypeToken<ArrayList<User>>(){}.getType();
        User.setAll(cargarLista("usuarios", tipoUsuarios));
    }

    public static void guardarCaja(CashRegister caja) {
        init();
        try (Writer writer = new FileWriter(CARPETA + "caja.json")) {
            gson.toJson(caja, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar caja: " + e.getMessage());
        }
    }

    public static CashRegister cargarCaja() {
        init();
        File archivo = new File(CARPETA + "caja.json");
        if (!archivo.exists()) {
            return new CashRegister(); 
        }
        try (Reader reader = new FileReader(archivo)) {
            CashRegister caja = gson.fromJson(reader, CashRegister.class);
            return caja != null ? caja : new CashRegister();
        } catch (IOException e) {
            System.out.println("Error al cargar caja: " + e.getMessage());
            return new CashRegister();
        }
    }
}