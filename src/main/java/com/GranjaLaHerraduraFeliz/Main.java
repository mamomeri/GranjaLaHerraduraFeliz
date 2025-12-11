package com.GranjaLaHerraduraFeliz;

import com.GranjaLaHerraduraFeliz.controller.ConsoleController;
import com.GranjaLaHerraduraFeliz.repository.InMemoryAnimalRepository;
import com.GranjaLaHerraduraFeliz.repository.InMemoryCustomerRepository;
import com.GranjaLaHerraduraFeliz.repository.InMemoryRentalRepository;
import com.GranjaLaHerraduraFeliz.service.AnimalService;
import com.GranjaLaHerraduraFeliz.service.CustomerService;
import com.GranjaLaHerraduraFeliz.service.RentalService;

/**
 * Punto de entrada principal de la aplicación.
 * <p>
 * Esta clase:
 * <ul>
 *     <li>Inicializa los repositorios en memoria.</li>
 *     <li>Construye los servicios de negocio.</li>
 *     <li>Configura el controlador de consola.</li>
 *     <li>Inicia la ejecución del menú interactivo.</li>
 * </ul>
 *
 * En versiones futuras del sistema, esta clase puede adaptarse
 * para inicializar una interfaz gráfica (GUI) o un servidor web,
 * pero para la versión actual gestiona únicamente la consola.
 *
 * @author Marcos
 * @since 1.0
 */
public class Main {

    /**
     * Método principal que inicializa todos los componentes y ejecuta la aplicación.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        // Repositorios en memoria
        var animalRepository = new InMemoryAnimalRepository();
        var customerRepository = new InMemoryCustomerRepository();
        var rentalRepository = new InMemoryRentalRepository();

        // Servicios
        var animalService = new AnimalService(animalRepository);
        var customerService = new CustomerService(customerRepository);
        var rentalService = new RentalService(animalRepository, customerRepository, rentalRepository);

        // Controlador de consola
        var appController = new ConsoleController(animalService, customerService, rentalService);

        // Ejecutar menú interactivo
        appController.run();
    }
}
