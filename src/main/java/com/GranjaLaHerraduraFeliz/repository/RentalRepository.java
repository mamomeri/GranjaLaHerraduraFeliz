package com.GranjaLaHerraduraFeliz.repository;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.Rental;

import java.util.List;

/**
 * Repositorio encargado de gestionar la persistencia de objetos {@link Rental}.
 * <p>
 * Define las operaciones necesarias para:
 * <ul>
 *     <li>Guardar o actualizar alquileres.</li>
 *     <li>Buscar alquileres por ID.</li>
 *     <li>Obtener todos los alquileres registrados.</li>
 *     <li>Consultar alquileres activos asociados a un animal.</li>
 * </ul>
 *
 * Este repositorio es una abstracción: su implementación puede ser en memoria,
 * base de datos, archivos o un servicio externo. El objetivo es desacoplar
 * la lógica de negocio de la forma en que se almacenan los datos.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public interface RentalRepository {

    /**
     * Guarda un alquiler en el repositorio.
     * <p>
     * Si el alquiler no tiene ID asignado, la implementación debe generarlo.
     * Si ya existe, debe actualizar los datos.
     *
     * @param rental El alquiler a guardar.
     * @return El alquiler guardado, posiblemente con ID asignado.
     */
    Rental save(Rental rental);

    /**
     * Busca un alquiler por su ID.
     *
     * @param id Identificador único del alquiler.
     * @return El alquiler encontrado, o {@code null} si no existe.
     */
    Rental findById(int id);

    /**
     * Obtiene todos los alquileres registrados.
     *
     * @return Lista completa de alquileres.
     */
    List<Rental> findAll();

    /**
     * Busca alquileres que estén activos para un animal específico.
     * <p>
     * Un alquiler activo se define como aquel cuyo {@code endTime} es {@code null}.
     *
     * @param animal Animal asociado al alquiler.
     * @return Lista de alquileres activos para ese animal (posiblemente vacía).
     */
    List<Rental> findActiveByAnimal(Animal animal);
}
