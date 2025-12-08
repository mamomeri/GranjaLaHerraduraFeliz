package com.GranjaLaHerraduraFeliz;

import com.GranjaLaHerraduraFeliz.controller.AppController;
import com.GranjaLaHerraduraFeliz.repository.InMemoryAnimalRepository;
import com.GranjaLaHerraduraFeliz.repository.InMemoryCustomerRepository;
import com.GranjaLaHerraduraFeliz.repository.InMemoryRentalRepository;
import com.GranjaLaHerraduraFeliz.service.AnimalService;
import com.GranjaLaHerraduraFeliz.service.CustomerService;
import com.GranjaLaHerraduraFeliz.service.RentalService;

public class Main {
    public static void main(String[] args) {

        // Repositorios en memoria
        var animalRepository = new InMemoryAnimalRepository();
        var customerRepository = new InMemoryCustomerRepository();
        var rentalRepository = new InMemoryRentalRepository();

        // Servicios
        var animalService = new AnimalService(animalRepository);
        var customerService = new CustomerService(customerRepository);
        var rentalService = new RentalService(animalRepository, customerRepository, rentalRepository);

        // Controlador
        var appController = new AppController(animalService, customerService, rentalService);

        // Ejecutar menú
        appController.run();
    }
}
