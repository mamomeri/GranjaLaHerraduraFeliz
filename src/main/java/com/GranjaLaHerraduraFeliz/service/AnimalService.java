package com.GranjaLaHerraduraFeliz.service;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.AnimalStatus;
import com.GranjaLaHerraduraFeliz.model.AnimalType;
import com.GranjaLaHerraduraFeliz.repository.AnimalRepository;

import java.util.List;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los animales.
 * <p>
 * Funciones principales:
 * <ul>
 *     <li>Registrar nuevos animales en la granja.</li>
 *     <li>Listar todos los animales registrados.</li>
 *     <li>Listar únicamente los animales disponibles para alquiler.</li>
 * </ul>
 *
 * Esta capa abstrae al controlador de los detalles del repositorio,
 * permitiendo aplicar reglas del negocio sin mezclar lógica de persistencia.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public class AnimalService {

    private final AnimalRepository animalRepository;

    /**
     * Crea una instancia del servicio inyectando el repositorio necesario.
     *
     * @param animalRepository Repositorio encargado de almacenar y gestionar animales.
     */
    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    /**
     * Registra un nuevo animal con estado inicial {@link AnimalStatus#AVAILABLE}.
     * <p>
     * Reglas del negocio:
     * <ul>
     *     <li>Todo animal nuevo entra como disponible.</li>
     *     <li>La asignación del ID queda a cargo del repositorio.</li>
     * </ul>
     *
     * @param name Nombre del animal.
     * @param type Tipo de animal, definido en {@link AnimalType}.
     * @return Animal registrado y persistido.
     */
    public Animal registerAnimal(String name, AnimalType type) {
        Animal animal = new Animal();
        animal.setName(name);
        animal.setType(type);
        animal.setStatus(AnimalStatus.AVAILABLE);

        return animalRepository.save(animal);
    }

    /**
     * Obtiene una lista con todos los animales registrados.
     *
     * @return Lista completa de {@link Animal}.
     */
    public List<Animal> listAllAnimals() {
        return animalRepository.findAll();
    }

    /**
     * Lista únicamente los animales cuyo estado es {@link AnimalStatus#AVAILABLE}.
     *
     * @return Lista de animales disponibles.
     */
    public List<Animal> listAvailableAnimals() {
        return animalRepository.findByStatus(AnimalStatus.AVAILABLE);
    }
}
