package com.GranjaLaHerraduraFeliz.service;

import com.GranjaLaHerraduraFeliz.Exception.AnimalNotAvailableException;
import com.GranjaLaHerraduraFeliz.Exception.RentalNotFoundException;
import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.AnimalStatus;
import com.GranjaLaHerraduraFeliz.model.Customer;
import com.GranjaLaHerraduraFeliz.model.Rental;
import com.GranjaLaHerraduraFeliz.model.RentalType;
import com.GranjaLaHerraduraFeliz.repository.AnimalRepository;
import com.GranjaLaHerraduraFeliz.repository.CustomerRepository;
import com.GranjaLaHerraduraFeliz.repository.RentalRepository;

import java.time.LocalDateTime;
import java.util.List;

public class RentalService {

    private final AnimalRepository animalRepository;
    private final CustomerRepository customerRepository;
    private final RentalRepository rentalRepository;

    public RentalService(AnimalRepository animalRepository,
                         CustomerRepository customerRepository,
                         RentalRepository rentalRepository) {
        this.animalRepository = animalRepository;
        this.customerRepository = customerRepository;
        this.rentalRepository = rentalRepository;
    }

    /**
     * Inicia un alquiler:
     * - Valida animal y cliente
     * - Verifica disponibilidad (AVAILABLE)
     * - Crea Rental (startTime viene del constructor por defecto)
     * - Cambia estado del animal a RENTED
     * - Guarda cambios
     */
    public Rental startRental(int animalId, int customerId, RentalType rentalType) {

        Animal animal = animalRepository.findById(animalId);
        if (animal == null) {
            // Si quieres, más adelante puedes crear una AnimalNotFoundException
            throw new IllegalArgumentException("Animal no encontrado con id: " + animalId);
        }

        if (animal.getStatus() != AnimalStatus.AVAILABLE) {
            // 👉 aquí usamos tu excepción personalizada
            throw new AnimalNotAvailableException(
                    "El animal con id " + animalId + " no está disponible para alquiler."
            );
        }

        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Cliente no encontrado con id: " + customerId);
        }

        // Crear el alquiler según tu clase actual
        Rental rental = new Rental(); // constructor ya pone startTime = now y endTime = null
        rental.setAnimal(animal);
        rental.setCustomer(customer);
        rental.setRentalType(rentalType);

        rental = rentalRepository.save(rental);

        // Actualizar estado del animal
        animal.setStatus(AnimalStatus.RENTED);
        animalRepository.save(animal);

        return rental;
    }

    /**
     * Finaliza un alquiler:
     * - Busca el rental
     * - Marca endTime = now
     * - Cambia el animal a AVAILABLE
     * - Guarda cambios
     */
    public Rental finishRental(int rentalId) {
        Rental rental = rentalRepository.findById(rentalId);
        if (rental == null) {
            // 👉 aquí usamos tu RentalNotFoundException
            throw new RentalNotFoundException("Alquiler no encontrado con id: " + rentalId);
        }

        if (rental.getEndTime() != null) {
            // Ya estaba finalizado
            return rental;
        }

        rental.setEndTime(LocalDateTime.now());

        // Restablecer animal a AVAILABLE
        Animal animal = rental.getAnimal();
        animal.setStatus(AnimalStatus.AVAILABLE);
        animalRepository.save(animal);

        rental = rentalRepository.save(rental);

        return rental;
    }

    public List<Rental> listAllRentals() {
        return rentalRepository.findAll();
    }
}
