package com.GranjaLaHerraduraFeliz.controller;

import com.GranjaLaHerraduraFeliz.Exception.AnimalNotAvailableException;
import com.GranjaLaHerraduraFeliz.Exception.RentalNotFoundException;
import com.GranjaLaHerraduraFeliz.model.AnimalType;
import com.GranjaLaHerraduraFeliz.model.RentalType;
import com.GranjaLaHerraduraFeliz.service.AnimalService;
import com.GranjaLaHerraduraFeliz.service.CustomerService;
import com.GranjaLaHerraduraFeliz.service.RentalService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    // Logger para seguir el flujo de uso de la aplicación desde la consola
    private static final Logger log = LoggerFactory.getLogger(ConsoleController.class);

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

        log.info("ConsoleController inicializado. Interfaz CLI lista para recibir comandos.");
        // Comentario: INFO → indica que la capa de presentación por consola está lista.
    }

    /**
     * Inicia el ciclo principal del programa,
     * mostrando opciones y ejecutando operaciones hasta que el usuario decida salir.
     */
    public void run() {
        boolean running = true;
        log.info("Inicio del bucle principal de la consola.");
        // Comentario: INFO → comienzo de una sesión de uso de la app.

        while (running) {
            printMenu();
            int option = readInt("Elige una opción: ");

            log.debug("Opción seleccionada por el usuario: {}", option);
            // Comentario: DEBUG → rastrea qué opciones usa más el usuario.

            switch (option) {
                case 1 -> registerAnimal();
                case 2 -> registerCustomer();
                case 3 -> startRental();
                case 4 -> finishRental();
                case 5 -> listAvailableAnimals();
                case 0 -> {
                    System.out.println("Saliendo del sistema...");
                    log.info("Usuario solicitó salir del sistema desde el menú.");
                    running = false;
                }
                default -> {
                    System.out.println("Opción no válida. Intenta de nuevo.");
                    log.warn("Opción de menú inválida ingresada: {}", option);
                    // Comentario: WARN → entrada del usuario incorrecta pero no grave.
                }
            }

            System.out.println();
        }

        log.info("Fin del bucle principal de la consola. Sesión terminada.");
        // Comentario: INFO → útil para auditoría de sesiones en logs.
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

        log.debug("Menú principal mostrado al usuario.");
        // Comentario: DEBUG → se puede usar para ver frecuencia de refresco de menú.
    }

    /** Lee un entero desde la consola con validación básica. */
    private int readInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            log.warn("Entrada inválida para número entero. Valor ingresado: {}", scanner.next());
            // Comentario: WARN → el usuario escribió algo no numérico.
            System.out.print("Introduce un número válido: ");
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // limpiar salto de línea
        log.debug("Número entero leído desde consola: {}", value);
        // Comentario: DEBUG → rastrea parámetros clave que entran al sistema.
        return value;
    }

    /** Lee una línea completa desde la consola. */
    private String readLine(String message) {
        System.out.print(message);
        String value = scanner.nextLine();
        log.debug("Cadena leída desde consola: '{}'", value);
        // Comentario: DEBUG → útil para depurar pero ojo con datos sensibles en otros contextos.
        return value;
    }

    /** Lógica del menú: registrar animal. */
    private void registerAnimal() {
        log.info("Opción seleccionada: registrar animal.");
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
                log.warn("Tipo de animal inválido ({}) → se usa HORSE por defecto.", typeOption);
                // Comentario: WARN → decisión de fallback documentada en logs.
                yield AnimalType.HORSE;
            }
        };

        var animal = animalService.registerAnimal(name, type);
        System.out.println("Animal registrado: " + animal);
        log.info("Animal registrado desde consola: {}", animal);
        // Comentario: INFO → refleja acción de negocio concreta disparada por el usuario.
    }

    /** Lógica del menú: registrar cliente. */
    private void registerCustomer() {
        log.info("Opción seleccionada: registrar cliente.");
        String fullName = readLine("Nombre completo del cliente: ");

        var customer = customerService.registerCustomer(fullName);
        System.out.println("Cliente registrado: " + customer);
        log.info("Cliente registrado desde consola: {}", customer);
    }

    /** Lógica del menú: iniciar alquiler. */
    private void startRental() {
        log.info("Opción seleccionada: crear alquiler.");
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
                log.warn("Tipo de alquiler inválido ({}) → se usa SHORT_RIDE por defecto.", rentalOption);
                yield RentalType.SHORT_RIDE;
            }
        };

        try {
            log.debug("Intentando crear alquiler. animalId={}, customerId={}, rentalType={}",
                    animalId, customerId, rentalType);

            var rental = rentalService.startRental(animalId, customerId, rentalType);
            System.out.println("Alquiler creado: " + rental);
            log.info("Alquiler creado correctamente: {}", rental);

        } catch (AnimalNotAvailableException e) {
            System.out.println("⚠ El animal no está disponible: " + e.getMessage());
            log.warn("Intento de alquilar animal no disponible. animalId={}, detalle={}",
                    animalId, e.getMessage());
            // Comentario: WARN → condición esperada de negocio (no es fallo del sistema).

        } catch (IllegalArgumentException e) {
            System.out.println("⚠ Error en los datos: " + e.getMessage());
            log.warn("Datos inválidos al crear alquiler. animalId={}, customerId={}, detalle={}",
                    animalId, customerId, e.getMessage());
            // Comentario: WARN → problema de validación / entrada del usuario.

        } catch (Exception e) {
            System.out.println("⚠ Error inesperado al crear alquiler: " + e.getMessage());
            log.error("Error inesperado al crear alquiler.", e);
            // Comentario: ERROR → se registra stacktrace completo para diagnóstico.
        }
    }

    /** Lógica del menú: finalizar alquiler. */
    private void finishRental() {
        log.info("Opción seleccionada: finalizar alquiler.");
        int rentalId = readInt("ID del alquiler: ");

        try {
            log.debug("Intentando finalizar alquiler con ID={}", rentalId);
            var rental = rentalService.finishRental(rentalId);
            System.out.println("Alquiler finalizado: " + rental);
            log.info("Alquiler finalizado correctamente: {}", rental);

        } catch (RentalNotFoundException e) {
            System.out.println("⚠ Alquiler no encontrado: " + e.getMessage());
            log.warn("Intento de finalizar alquiler inexistente. rentalId={}, detalle={}",
                    rentalId, e.getMessage());

        } catch (Exception e) {
            System.out.println("⚠ Error inesperado al finalizar alquiler: " + e.getMessage());
            log.error("Error inesperado al finalizar alquiler con ID=" + rentalId, e);
        }
    }

    /** Lógica del menú: listar animales disponibles. */
    private void listAvailableAnimals() {
        log.info("Opción seleccionada: listar animales disponibles.");
        var animals = animalService.listAvailableAnimals();
        if (animals.isEmpty()) {
            System.out.println("No hay animales disponibles.");
            log.info("Consulta de animales disponibles → lista vacía.");
        } else {
            System.out.println("Animales disponibles:");
            animals.forEach(System.out::println);
            log.info("Se listaron {} animales disponibles.", animals.size());
        }
    }
}
