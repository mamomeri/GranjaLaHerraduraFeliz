package com.GranjaLaHerraduraFeliz.model;

/**
 * Representa un animal disponible en la granja para ser alquilado.
 * <p>
 * Cada animal posee:
 * <ul>
 *     <li>Un identificador único ({@code id}).</li>
 *     <li>Un nombre ({@code name}).</li>
 *     <li>Un tipo ({@link AnimalType}).</li>
 *     <li>Un estado ({@link AnimalStatus}).</li>
 * </ul>
 *
 * Esta clase es utilizada por el sistema para gestionar:
 * <ul>
 *     <li>Disponibilidad para alquiler.</li>
 *     <li>Operaciones de renta en {@code RentalService}.</li>
 *     <li>Listados y búsquedas en los repositorios.</li>
 * </ul>
 *
 * Es un componente central del dominio del sistema.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public class Animal {

    /** Identificador único del animal. */
    int id;

    /** Nombre del animal (opcionalmente asignado por la granja). */
    String name;

    /** Tipo de animal (caballo, burro, cerdito, etc.). */
    AnimalType type;

    /** Estado actual del animal (disponible o alquilado). */
    AnimalStatus status;

    /**
     * Constructor vacío.
     * <p>
     * Permite construir un objeto de forma incremental desde los servicios.
     */
    public Animal() {
    }

    /**
     * Constructor completo para inicializar un animal con valores definidos.
     *
     * @param id Identificador del animal.
     * @param name Nombre del animal.
     * @param type Tipo de animal.
     * @param status Estado del animal.
     */
    public Animal(int id, String name, AnimalType type, AnimalStatus status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
    }

    /** @return ID del animal. */
    public int getId() {
        return id;
    }

    /** @param id Nuevo ID del animal. */
    public void setId(int id) {
        this.id = id;
    }

    /** @return Nombre del animal. */
    public String getName() {
        return name;
    }

    /** @param name Define el nombre del animal. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return Tipo del animal. */
    public AnimalType getType() {
        return type;
    }

    /** @param type Tipo de animal a asignar. */
    public void setType(AnimalType type) {
        this.type = type;
    }

    /** @return Estado del animal. */
    public AnimalStatus getStatus() {
        return status;
    }

    /** @param status Define el estado del animal. */
    public void setStatus(AnimalStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
