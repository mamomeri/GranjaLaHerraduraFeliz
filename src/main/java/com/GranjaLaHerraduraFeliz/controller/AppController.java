package com.GranjaLaHerraduraFeliz.controller;

import com.GranjaLaHerraduraFeliz.Exception.AnimalNotAvailableException;
import com.GranjaLaHerraduraFeliz.Exception.RentalNotFoundException;
import com.GranjaLaHerraduraFeliz.model.AnimalType;
import com.GranjaLaHerraduraFeliz.model.RentalType;
import com.GranjaLaHerraduraFeliz.service.AnimalService;
import com.GranjaLaHerraduraFeliz.service.CustomerService;
import com.GranjaLaHerraduraFeliz.service.RentalService;

import java.util.Scanner;

public class AppController {

    private final AnimalService animalService;
    private final CustomerService customerService;
    private final RentalService rentalService;
    private final Scanner scanner;

    public AppController(AnimalService animalService,
                         CustomerService customerService,
                         RentalService rentalService) {
        this.animalService = animalService;
        this.customerService = customerService;
        this.rentalService = rentalService;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            int option = readInt("Elige una opción: ");

            switch (option) {
                case 1 -> registerAnimal();
                case 2 -> registerCustomer();
                case 3 -> startRental();
                case 4 -> finishRental();
                case 5 -> listAvailableAnimals();
                case 0 -> {
                    System.out.println("Saliendo del sistema...");
                    running = false;
                }
                default -> System.out.println("Opción no válida. Intenta de nuevo.");
            }

            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("===== Granja La Herradura Feliz =====");
        System.out.println("1. Registrar animal");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Crear alquiler");
        System.out.println("4. Finalizar alquiler");
        System.out.println("5. Listar animales disponibles");
        System.out.println("0. Salir");
    }

    private int readInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.print("Introduce un número válido: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // limpiar salto de línea
        return value;
    }

    private String readLine(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    // 1) Registrar animal
    private void registerAnimal() {
        String name = readLine("Nombre del animal: ");

        System.out.println("Tipo de animal:");
        System.out.println("1. HORSE");
        System.out.println("2. DONKEY");
        System.out.println("3. PIG");
        int typeOption = readInt("Elige tipo: ");

        AnimalType type = switch (typeOption) {
            case 1 -> AnimalType.HORSE;
            case 2 -> AnimalType.DONKEY;
            case 3 -> AnimalType.PIG;
            default -> {
                System.out.println("Tipo inválido, usando HORSE por defecto.");
                yield AnimalType.HORSE;
            }
        };

        var animal = animalService.registerAnimal(name, type);
        System.out.println("Animal registrado: " + animal);
    }

    // 2) Registrar cliente
    private void registerCustomer() {
        String fullName = readLine("Nombre completo del cliente: ");

        var customer = customerService.registerCustomer(fullName);
        System.out.println("Cliente registrado: " + customer);
    }

    // 3) Crear alquiler
    private void startRental() {
        int animalId = readInt("ID del animal: ");
        int customerId = readInt("ID del cliente: ");

        System.out.println("Tipo de alquiler:");
        System.out.println("1. SHORT_RIDE");
        System.out.println("2. HOURLY");
        int rentalOption = readInt("Elige tipo: ");

        RentalType rentalType = switch (rentalOption) {
            case 1 -> RentalType.SHORT_RIDE;
            case 2 -> RentalType.HOURLY;
            default -> {
                System.out.println("Tipo inválido, usando SHORT_RIDE por defecto.");
                yield RentalType.SHORT_RIDE;
            }
        };

        try {
            var rental = rentalService.startRental(animalId, customerId, rentalType);
            System.out.println("Alquiler creado: " + rental);

        } catch (AnimalNotAvailableException e) {
            System.out.println("⚠ El animal no está disponible: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            // Animal o cliente no encontrados, o datos inválidos
            System.out.println("⚠ Error en los datos: " + e.getMessage());

        } catch (Exception e) {
            // Cualquier otra cosa inesperada
            System.out.println("⚠ Error inesperado al crear alquiler: " + e.getMessage());
        }
    }

    // 4) Finalizar alquiler
    private void finishRental() {
        int rentalId = readInt("ID del alquiler: ");

        try {
            var rental = rentalService.finishRental(rentalId);
            System.out.println("Alquiler finalizado: " + rental);

        } catch (RentalNotFoundException e) {
            System.out.println("⚠ Alquiler no encontrado: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("⚠ Error inesperado al finalizar alquiler: " + e.getMessage());
        }
    }

    // 5) Listar animales disponibles
    private void listAvailableAnimals() {
        var animals = animalService.listAvailableAnimals();
        if (animals.isEmpty()) {
            System.out.println("No hay animales disponibles.");
        } else {
            System.out.println("Animales disponibles:");
            animals.forEach(System.out::println);
        }
    }
}
