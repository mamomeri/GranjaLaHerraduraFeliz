package com.GranjaLaHerraduraFeliz.controller;

import com.GranjaLaHerraduraFeliz.Exception.AnimalNotAvailableException;
import com.GranjaLaHerraduraFeliz.Exception.RentalNotFoundException;
import com.GranjaLaHerraduraFeliz.model.AnimalType;
import com.GranjaLaHerraduraFeliz.model.RentalType;
import com.GranjaLaHerraduraFeliz.service.AnimalService;
import com.GranjaLaHerraduraFeliz.service.CustomerService;
import com.GranjaLaHerraduraFeliz.service.RentalService;

import java.util.Scanner;

/**
 * Controlador encargado de manejar la interacción del usuario
 * mediante la interfaz de consola (CLI).
 * <p>
 * Este controlador:
 * <ul>
 *     <li>Muestra menús de opciones.</li>
 *     <li>Solicita datos por consola.</li>
 *     <li>Invoca a los servicios de negocio.</li>
 *     <li>Maneja errores y excepciones del flujo.</li>
 * </ul>
 *
 * Su objetivo es únicamente gestionar la experiencia por la terminal.
 * La lógica de negocio está encapsulada en los servicios.
 *
 * <p>
 * En versiones futuras del sistema (por ejemplo, una interfaz gráfica con JavaFX),
 * este controlador podrá convivir junto a otros controladores sin necesidad
 * de ser eliminado.
 * </p>
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public class ConsoleController {

    private final AnimalService animalService;
    private final CustomerService customerService;
    private final RentalService rentalService;
    private final Scanner scanner;

    /**
     * Crea un controlador para la interfaz de consola.
     *
     * @param animalService Servicio de animales.
     * @param customerService Servicio de clientes.
     * @param rentalService Servicio de alquileres.
     */
    public ConsoleController(AnimalService animalService,
                             CustomerService customerService,
                             RentalService rentalService) {
        this.animalService = animalService;
        this.customerService = customerService;
        this.rentalService = rentalService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia el ciclo principal del programa,
     * mostrando opciones y ejecutando operaciones hasta que el usuario decida salir.
     */
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

    /** Imprime el menú principal. */
    private void printMenu() {
        System.out.println("===== Granja La Herradura Feliz =====");
        System.out.println("1. Registrar animal");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Crear alquiler");
        System.out.println("4. Finalizar alquiler");
        System.out.println("5. Listar animales disponibles");
        System.out.println("0. Salir");
    }

    /** Lee un entero desde la consola con validación básica. */
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

    /** Lee una línea completa desde la consola. */
    private String readLine(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /** Lógica del menú: registrar animal. */
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

    /** Lógica del menú: registrar cliente. */
    private void registerCustomer() {
        String fullName = readLine("Nombre completo del cliente: ");
        var customer = customerService.registerCustomer(fullName);
        System.out.println("Cliente registrado: " + customer);
    }

    /** Lógica del menú: iniciar alquiler. */
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
            System.out.println("⚠ Error en los datos: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("⚠ Error inesperado al crear alquiler: " + e.getMessage());
        }
    }

    /** Lógica del menú: finalizar alquiler. */
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

    /** Lógica del menú: listar animales disponibles. */
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
