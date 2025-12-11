package com.GranjaLaHerraduraFeliz.repository;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.AnimalStatus;

import java.util.List;

/**
 * Repositorio encargado de gestionar la persistencia de objetos {@link Animal}.
 * <p>
 * Esta interfaz define las operaciones necesarias para manejar animales dentro del sistema:
 * <ul>
 *     <li>Registrar o actualizar animales.</li>
 *     <li>Buscar animales por ID.</li>
 *     <li>Obtener la lista completa de animales.</li>
 *     <li>Buscar animales filtrados por estado ({@link AnimalStatus}).</li>
 * </ul>
 *
 * El uso de una interfaz permite que diferentes implementaciones puedan reemplazarse
 * sin afectar la capa de servicios: almacenamiento en memoria, base de datos SQL,
 * archivos, o incluso servicios externos.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public interface AnimalRepository {

    /**
     * Guarda un animal en el repositorio.
     * <p>
     * Dependiendo de la implementación:
     * <ul>
     *     <li>Si el animal no tiene ID asignado, debe generarse uno nuevo.</li>
     *     <li>Si ya tiene ID, se actualizará el registro existente.</li>
     * </ul>
     *
     * @param animal Animal a registrar o actualizar.
     * @return El animal guardado.
     */
    Animal save(Animal animal);

    /**
     * Busca un animal por su identificador único.
     *
     * @param id ID del animal.
     * @return El animal encontrado, o {@code null} si no existe.
     */
    Animal findById(int id);

    /**
     * Obtiene una lista con todos los animales almacenados en el repositorio.
     *
     * @return Lista completa de {@link Animal}.
     */
    List<Animal> findAll();

    /**
     * Busca animales cuyo estado sea igual al proporcionado.
     * <p>
     * Los estados posibles se definen en {@link AnimalStatus}.
     *
     * @param status Estado por el cual se desea filtrar.
     * @return Lista de animales que están en el estado indicado.
     */
    List<Animal> findByStatus(AnimalStatus status);
}
