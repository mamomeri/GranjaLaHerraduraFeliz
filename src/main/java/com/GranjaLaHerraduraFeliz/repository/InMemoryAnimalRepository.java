package com.GranjaLaHerraduraFeliz.repository;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.AnimalStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación en memoria del repositorio de {@link Animal}.
 * <p>
 * Esta clase utiliza una lista interna para almacenar animales sin necesidad
 * de una base de datos. Es una solución simple y eficaz para prototipos,
 * pruebas iniciales o aplicaciones pequeñas.
 * <p>
 * Características principales:
 * <ul>
 *     <li>Asigna IDs incrementales automáticamente a nuevos registros.</li>
 *     <li>Permite guardar y actualizar animales.</li>
 *     <li>Permite consultas básicas como obtener todos o buscar por estado.</li>
 *     <li>No persiste datos entre ejecuciones.</li>
 * </ul>
 *
 * El uso de la interfaz {@link AnimalRepository} permite reemplazar esta
 * implementación por otra persistente (BD) sin cambiar la lógica del servicio.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public class InMemoryAnimalRepository implements AnimalRepository {

    /** Lista interna donde se almacenan los animales. */
    private final List<Animal> animals = new ArrayList<>();

    /** Contador utilizado para generar IDs únicos de manera incremental. */
    private int nextId = 1;

    /**
     * Guarda un animal en el repositorio.
     * <p>
     * Reglas:
     * <ul>
     *     <li>Si el animal tiene ID 0, se considera un registro nuevo y se le asigna uno.</li>
     *     <li>Si el animal tiene un ID existente, se actualiza el registro correspondiente.</li>
     *     <li>Si el ID no coincide con ninguno almacenado, se agrega igualmente como nuevo.</li>
     * </ul>
     *
     * @param animal Animal a guardar o actualizar.
     * @return El animal guardado.
     */
    @Override
    public Animal save(Animal animal) {
        if (animal.getId() == 0) {
            // Registrar un nuevo animal
            animal.setId(nextId++);
            animals.add(animal);
        } else {
            // Actualizar animal existente
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i).getId() == animal.getId()) {
                    animals.set(i, animal);
                    return animal;
                }
            }
            // Si no existe el ID, se agrega igual (flexible)
            animals.add(animal);
        }
        return animal;
    }

    /**
     * Busca un animal por su ID.
     *
     * @param id Identificador del animal.
     * @return El animal encontrado o {@code null} si no existe.
     */
    @Override
    public Animal findById(int id) {
        for (Animal animal : animals) {
            if (animal.getId() == id) {
                return animal;
            }
        }
        return null;
    }

    /**
     * Obtiene una lista con todos los animales registrados.
     * <p>
     * Se retorna una copia defensiva para evitar modificaciones no deseadas
     * desde fuera del repositorio.
     *
     * @return Lista de todos los animales almacenados.
     */
    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(animals);
    }

    /**
     * Devuelve los animales cuyo estado coincide con el solicitado.
     *
     * @param status Estado a filtrar (por ejemplo {@link AnimalStatus#AVAILABLE} o {@link AnimalStatus#RENTED}).
     * @return Lista de animales que cumplen el criterio.
     */
    @Override
    public List<Animal> findByStatus(AnimalStatus status) {
        List<Animal> result = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.getStatus() == status) {
                result.add(animal);
            }
        }
        return result;
    }
}
