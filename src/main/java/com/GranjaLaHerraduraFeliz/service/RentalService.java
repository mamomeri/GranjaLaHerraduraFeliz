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

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los alquileres.
 * <p>
 * Funcionalidades principales:
 * <ul>
 *     <li>Iniciar un alquiler validando animal, cliente y disponibilidad.</li>
 *     <li>Finalizar un alquiler registrando la fecha de devolución.</li>
 *     <li>Actualizar el estado del animal según corresponda.</li>
 *     <li>Listar los alquileres registrados.</li>
 * </ul>
 *
 * Este servicio actúa como intermediario entre los controladores y los repositorios,
 * encapsulando toda la lógica relacionada con el proceso de alquiler.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public class RentalService {

    private final AnimalRepository animalRepository;
    private final CustomerRepository customerRepository;
    private final RentalRepository rentalRepository;

    /**
     * Crea un servicio de alquiler inyectando los repositorios necesarios.
     *
     * @param animalRepository   Repositorio para gestión de animales.
     * @param customerRepository Repositorio para gestión de clientes.
     * @param rentalRepository   Repositorio para gestión de alquileres.
     */
    public RentalService(AnimalRepository animalRepository,
                         CustomerRepository customerRepository,
                         RentalRepository rentalRepository) {
        this.animalRepository = animalRepository;
        this.customerRepository = customerRepository;
        this.rentalRepository = rentalRepository;
    }

    /**
     * Inicia un nuevo alquiler validando animal, cliente y disponibilidad.
     * <p>
     * Reglas del negocio:
     * <ul>
     *     <li>El animal debe existir.</li>
     *     <li>El animal debe estar en estado {@code AVAILABLE}.</li>
     *     <li>El cliente debe existir.</li>
     *     <li>El alquiler inicia con {@code startTime = now} y {@code endTime = null}.</li>
     *     <li>El animal cambia su estado a {@code RENTED}.</li>
     * </ul>
     *
     * @param animalId   ID del animal a alquilar.
     * @param customerId ID del cliente que realiza el alquiler.
     * @param rentalType Tipo de alquiler (SHORT_RIDE, HOURLY, etc.).
     * @return El nuevo alquiler registrado.
     *
     * @throws IllegalArgumentException      Si el animal o el cliente no existen.
     * @throws AnimalNotAvailableException   Si el animal no está disponible.
     */
    public Rental startRental(int animalId, int customerId, RentalType rentalType) {

        // Validar existencia del animal
        Animal animal = animalRepository.findById(animalId);
        if (animal == null) {
            throw new IllegalArgumentException("Animal no encontrado con id: " + animalId);
        }

        // Validar disponibilidad
        if (animal.getStatus() != AnimalStatus.AVAILABLE) {
            throw new AnimalNotAvailableException(
                    "El animal con id " + animalId + " no está disponible para alquiler."
            );
        }

        // Validar existencia del cliente
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Cliente no encontrado con id: " + customerId);
        }

        // Crear alquiler (startTime y endTime se manejan desde el constructor)
        Rental rental = new Rental();
        rental.setAnimal(animal);
        rental.setCustomer(customer);
        rental.setRentalType(rentalType);

        rental = rentalRepository.save(rental);

        // Cambiar estado del animal
        animal.setStatus(AnimalStatus.RENTED);
        animalRepository.save(animal);

        return rental;
    }

    /**
     * Finaliza un alquiler existente.
     * <p>
     * Reglas del negocio:
     * <ul>
     *     <li>El alquiler debe existir.</li>
     *     <li>Si ya estaba finalizado, se devuelve tal cual.</li>
     *     <li>Se registra {@code endTime = now}.</li>
     *     <li>El animal asociado vuelve a estado {@code AVAILABLE}.</li>
     * </ul>
     *
     * @param rentalId ID del alquiler a finalizar.
     * @return El alquiler actualizado.
     *
     * @throws RentalNotFoundException Si el alquiler no existe.
     */
    public Rental finishRental(int rentalId) {
        Rental rental = rentalRepository.findById(rentalId);
        if (rental == null) {
            throw new RentalNotFoundException("Alquiler no encontrado con id: " + rentalId);
        }

        // Si ya estaba finalizado
        if (rental.getEndTime() != null) {
            return rental;
        }

        rental.setEndTime(LocalDateTime.now());

        // Restablecer estado del animal
        Animal animal = rental.getAnimal();
        animal.setStatus(AnimalStatus.AVAILABLE);
        animalRepository.save(animal);

        rental = rentalRepository.save(rental);

        return rental;
    }

    /**
     * Obtiene un listado completo de alquileres registrados.
     *
     * @return Lista de {@link Rental}.
     */
    public List<Rental> listAllRentals() {
        return rentalRepository.findAll();
    }
}
