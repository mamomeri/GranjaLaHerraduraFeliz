package com.GranjaLaHerraduraFeliz.model;

/**
 * Representa a un cliente de la granja.
 * <p>
 * Un cliente es la persona que alquila animales dentro del sistema.
 * Cada cliente posee:
 * <ul>
 *     <li>Un identificador único ({@code id}).</li>
 *     <li>Un nombre completo ({@code fullName}).</li>
 * </ul>
 *
 * Esta clase se utiliza tanto en la capa de negocio como en los repositorios
 * y servicios encargados de gestionar el proceso de alquiler.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public class Customer {

    /** Identificador único del cliente. */
    int id;

    /** Nombre completo del cliente. */
    String fullName;

    /**
     * Constructor por defecto.
     * <p>
     * Útil cuando se construyen clientes de forma incremental desde los servicios.
     */
    public Customer() {
    }

    /**
     * Constructor completo.
     *
     * @param id ID del cliente.
     * @param fullName Nombre completo del cliente.
     */
    public Customer(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    /** @return ID del cliente. */
    public int getId() {
        return id;
    }

    /** @param id Nuevo ID para el cliente. */
    public void setId(int id) {
        this.id = id;
    }

    /** @return Nombre completo del cliente. */
    public String getFullName() {
        return fullName;
    }

    /** @param fullName Define el nombre completo del cliente. */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
