package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer {
    private String customerId;
    private String name;
    private String phone;
    private String idNumber;
    private String address;

    private static List<Customer> listaClientes = new ArrayList<>();

    public Customer(String customerId, String name, String phone, String idNumber, String address) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.idNumber = idNumber;
        this.address = address;
    }

    public void register() {
        listaClientes.add(this);
    }
    
    public Customer() { } 

    public static void setAll(List<Customer> nuevaLista) {
        listaClientes = nuevaLista;
    }

    public static Customer search(String criterio) {
        for (Customer c : listaClientes) {
            if (c.getCustomerId().equalsIgnoreCase(criterio) || c.getIdNumber().equals(criterio)) {
                return c;
            }
        }
        return null;
    }

    public static List<Customer> getAll() {
        return listaClientes;
    }

    public static void menuGestion(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTION DE CLIENTES ---");
            System.out.println("1. Registrar nuevo Cliente");
            System.out.println("2. Buscar Cliente");
            System.out.println("3. Listar todos los Clientes");
            System.out.println("0. Volver al menu principal");
            System.out.print("Opcion: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
                continue;
            }

            switch (opcion) {
                case 1: registrarPorConsola(scanner); break;
                case 2: buscarPorConsola(scanner); break;
                case 3: listarPorConsola(); break;
                case 0: volver = true; break;
                default: System.out.println("Opcion no valida.");
            }
        }
    }

    private static void registrarPorConsola(Scanner scanner) {
        System.out.println("\n--- REGISTRAR NUEVO CLIENTE ---");
        System.out.print("ID del Cliente: ");
        String id = scanner.nextLine();
        
        if (search(id) != null) {
            System.out.println("Error: Ya existe un cliente con ese ID.");
            return;
        }

        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Telefono: ");
        String tel = scanner.nextLine();
        System.out.print("Cedula/DNI: ");
        String cedula = scanner.nextLine();
        System.out.print("Direccion: ");
        String direccion = scanner.nextLine();

        Customer nuevoCliente = new Customer(id, nombre, tel, cedula, direccion);
        nuevoCliente.register(); 
        System.out.println("¡Cliente registrado con exito!");
    }

    private static void buscarPorConsola(Scanner scanner) {
        System.out.println("\n--- BUSCAR CLIENTE ---");
        System.out.print("Ingrese el ID o la Cedula del cliente: ");
        String criterio = scanner.nextLine();

        Customer encontrado = search(criterio); 

        if (encontrado != null) {
            System.out.println("\n--- CLIENTE ENCONTRADO ---");
            System.out.println("ID: " + encontrado.getCustomerId());
            System.out.println("Nombre: " + encontrado.getName());
            System.out.println("Telefono: " + encontrado.getPhone());
            System.out.println("Cedula: " + encontrado.getIdNumber());
            System.out.println("Direccion: " + encontrado.getAddress());
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void listarPorConsola() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        if (listaClientes.isEmpty()) {
            System.out.println("No hay clientes registrados aun.");
        } else {
            for (Customer c : listaClientes) {
                System.out.println("- [" + c.getCustomerId() + "] " + c.getName() + " | Tel: " + c.getPhone());
            }
        }
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getIdNumber() { return idNumber; }
    public String getAddress() { return address; }
}