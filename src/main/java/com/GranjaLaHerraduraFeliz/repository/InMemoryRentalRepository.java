package com.GranjaLaHerraduraFeliz.repository;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.Rental;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación en memoria del repositorio de {@link Rental}.
 * <p>
 * Utiliza una lista interna para almacenar los alquileres registrados,
 * simulando un mecanismo de persistencia básico sin base de datos.
 * <p>
 * Características:
 * <ul>
 *     <li>Asigna IDs incrementales automáticamente.</li>
 *     <li>Permite guardar y actualizar alquileres.</li>
 *     <li>Permite buscar alquileres activos por animal.</li>
 *     <li>No es persistente: los datos se pierden al reiniciar la aplicación.</li>
 * </ul>
 *
 * Esta implementación es ideal para pruebas, prototipos o la versión inicial del sistema.
 * Posteriormente puede reemplazarse por una versión basada en base de datos sin
 * modificar el código del servicio, gracias al uso de la interfaz {@link RentalRepository}.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public class InMemoryRentalRepository implements RentalRepository {

    /** Lista interna que almacena los alquileres. */
    private final List<Rental> rentals = new ArrayList<>();

    /** Contador para asignar IDs automáticos de forma incremental. */
    private int nextId = 1;

    /**
     * Guarda un alquiler en la lista interna.
     * <p>
     * Reglas:
     * <ul>
     *     <li>Si el ID es 0, se considera un alquiler nuevo; se asigna un ID y se agrega a la lista.</li>
     *     <li>Si el ID ya existe, se actualiza el alquiler correspondiente.</li>
     *     <li>Si el ID no existe en la lista, se agrega como nuevo (comportamiento flexible).</li>
     * </ul>
     *
     * @param rental Alquiler a guardar o actualizar.
     * @return El objeto {@link Rental} guardado.
     */
    @Override
    public Rental save(Rental rental) {
        if (rental.getId() == 0) {
            rental.setId(nextId++);
            rentals.add(rental);
        } else {
            // Intentar actualizar alquiler existente
            for (int i = 0; i < rentals.size(); i++) {
                if (rentals.get(i).getId() == rental.getId()) {
                    rentals.set(i, rental);
                    return rental;
                }
            }
            // Si no existe, se agrega igual
            rentals.add(rental);
        }
        return rental;
    }

    /**
     * Busca un alquiler por su ID.
     *
     * @param id Identificador del alquiler.
     * @return El alquiler encontrado o {@code null} si no existe.
     */
    @Override
    public Rental findById(int id) {
        for (Rental rental : rentals) {
            if (rental.getId() == id) {
                return rental;
            }
        }
        return null;
    }

    /**
     * Devuelve una copia de la lista con todos los alquileres.
     * <p>
     * Se retorna una nueva {@link ArrayList} para evitar que código externo
     * modifique la lista original accidentalmente.
     *
     * @return Lista completa de alquileres.
     */
    @Override
    public List<Rental> findAll() {
        return new ArrayList<>(rentals);
    }

    /**
     * Busca alquileres activos asociados a un animal.
     * <p>
     * Un alquiler activo se define como aquel cuyo {@code endTime} es {@code null}.
     *
     * @param animal Animal cuyo alquiler activo se quiere buscar.
     * @return Lista de alquileres en curso para el animal indicado.
     */
    @Override
    public List<Rental> findActiveByAnimal(Animal animal) {
        List<Rental> result = new ArrayList<>();
        for (Rental rental : rentals) {
            boolean sameAnimal = rental.getAnimal().getId() == animal.getId();
            boolean active = rental.getEndTime() == null;
            if (sameAnimal && active) {
                result.add(rental);
            }
        }
        return result;
    }
}
