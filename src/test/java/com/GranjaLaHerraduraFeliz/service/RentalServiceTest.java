package com.GranjaLaHerraduraFeliz.service;

import com.GranjaLaHerraduraFeliz.Exception.AnimalNotAvailableException;
import com.GranjaLaHerraduraFeliz.Exception.RentalNotFoundException;
import com.GranjaLaHerraduraFeliz.model.*;
import com.GranjaLaHerraduraFeliz.repository.InMemoryAnimalRepository;
import com.GranjaLaHerraduraFeliz.repository.InMemoryCustomerRepository;
import com.GranjaLaHerraduraFeliz.repository.InMemoryRentalRepository;
import com.GranjaLaHerraduraFeliz.repository.AnimalRepository;
import com.GranjaLaHerraduraFeliz.repository.CustomerRepository;
import com.GranjaLaHerraduraFeliz.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link RentalService}.
 * <p>
 * El objetivo es validar la lógica de negocio típica de la granja:
 * creación de alquileres, control de disponibilidad y cierre de alquileres.
 */
public class RentalServiceTest {

    private AnimalRepository animalRepository;
    private CustomerRepository customerRepository;
    private RentalRepository rentalRepository;

    private RentalService rentalService;

    @BeforeEach
    void setUp() {
        animalRepository = new InMemoryAnimalRepository();
        customerRepository = new InMemoryCustomerRepository();
        rentalRepository = new InMemoryRentalRepository();

        rentalService = new RentalService(animalRepository, customerRepository, rentalRepository);
    }

    @Test
    void startRental_createsRentalAndMarksAnimalAsRented() {
        // Arrange: un caballo disponible y un cliente típico de la granja
        Animal horse = new Animal();
        horse.setName("Relámpago");
        horse.setType(AnimalType.HORSE);
        horse.setStatus(AnimalStatus.AVAILABLE);
        horse = animalRepository.save(horse);

        Customer customer = new Customer();
        customer.setFullName("Ana Cliente");
        customer = customerRepository.save(customer);

        // Act
        Rental rental = rentalService.startRental(horse.getId(), customer.getId(), RentalType.SHORT_RIDE);

        // Assert
        assertNotNull(rental.getId(), "El alquiler debería tener un ID asignado");
        assertEquals(horse.getId(), rental.getAnimal().getId());
        assertEquals(customer.getId(), rental.getCustomer().getId());
        assertNotNull(rental.getStartTime(), "El alquiler debería tener hora de inicio");
        assertNull(rental.getEndTime(), "El alquiler recién creado no debería tener hora de fin");
        assertEquals(AnimalStatus.RENTED,
                animalRepository.findById(horse.getId()).getStatus(),
                "El animal debería quedar en estado RENTED");
    }

    @Test
    void startRental_throwsWhenAnimalNotAvailable() {
        // Arrange
        Animal donkey = new Animal();
        donkey.setName("Burro Valiente");
        donkey.setType(AnimalType.DONKEY);
        donkey.setStatus(AnimalStatus.RENTED);
        donkey = animalRepository.save(donkey);

        Customer customer = new Customer();
        customer.setFullName("Carlos Cliente");
        customer = customerRepository.save(customer);

        // Las variables deben ser efectivamente finales
        final int donkeyId = donkey.getId();
        final int customerId = customer.getId();

        // Act & Assert
        assertThrows(AnimalNotAvailableException.class,
                () -> rentalService.startRental(donkeyId, customerId, RentalType.HOURLY)
        );
    }


    @Test
    void finishRental_setsEndTimeAndMarksAnimalAvailable() {
        // Arrange: caballo disponible + cliente + alquiler activo
        Animal horse = new Animal();
        horse.setName("Tornado");
        horse.setType(AnimalType.HORSE);
        horse.setStatus(AnimalStatus.AVAILABLE);
        horse = animalRepository.save(horse);

        Customer customer = new Customer();
        customer.setFullName("Beatriz Cliente");
        customer = customerRepository.save(customer);

        Rental rental = rentalService.startRental(horse.getId(), customer.getId(), RentalType.SHORT_RIDE);

        // Act
        Rental finished = rentalService.finishRental(rental.getId());

        // Assert
        assertNotNull(finished.getEndTime(), "El alquiler finalizado debería tener hora de fin");
        assertEquals(AnimalStatus.AVAILABLE,
                animalRepository.findById(horse.getId()).getStatus(),
                "El animal debería volver a AVAILABLE");
    }

    @Test
    void finishRental_throwsWhenRentalNotFound() {
        // Act & Assert
        assertThrows(RentalNotFoundException.class, () ->
                rentalService.finishRental(9999)); // ID inexistente
    }

    @Test
    void listAllRentals_returnsAllCreatedRentals() {
        // Arrange: creamos dos animales y dos alquileres
        Animal horse1 = new Animal();
        horse1.setName("Relámpago");
        horse1.setType(AnimalType.HORSE);
        horse1.setStatus(AnimalStatus.AVAILABLE);
        horse1 = animalRepository.save(horse1);

        Animal horse2 = new Animal();
        horse2.setName("Centella");
        horse2.setType(AnimalType.HORSE);
        horse2.setStatus(AnimalStatus.AVAILABLE);
        horse2 = animalRepository.save(horse2);

        Customer customer = new Customer();
        customer.setFullName("Cliente Regular");
        customer = customerRepository.save(customer);

        rentalService.startRental(horse1.getId(), customer.getId(), RentalType.SHORT_RIDE);
        rentalService.startRental(horse2.getId(), customer.getId(), RentalType.HOURLY);

        // Act
        List<Rental> rentals = rentalService.listAllRentals();

        // Assert
        assertEquals(2, rentals.size(), "Debería haber dos alquileres registrados");
    }
}
