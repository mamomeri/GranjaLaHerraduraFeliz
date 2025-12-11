package com.GranjaLaHerraduraFeliz.model;

import java.time.LocalDateTime;

/**
 * Representa un alquiler realizado en la granja.
 * <p>
 * Un {@code Rental} vincula:
 * <ul>
 *     <li>Un {@link Animal} alquilado.</li>
 *     <li>Un {@link Customer} que realiza el alquiler.</li>
 *     <li>El momento de inicio y finalización.</li>
 *     <li>El tipo de alquiler ({@link RentalType}).</li>
 * </ul>
 *
 * El alquiler se considera <b>activo</b> mientras {@code endTime} sea {@code null}.
 * Los repositorios y servicios utilizan esta condición para determinar disponibilidad.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public class Rental {

    /** Identificador único del alquiler. */
    int id;

    /** Animal que está siendo alquilado. */
    Animal animal;

    /** Cliente que realiza el alquiler. */
    Customer customer;

    /** Momento en que inicia el alquiler. */
    LocalDateTime startTime;

    /** Momento en que finaliza el alquiler; es {@code null} mientras esté activo. */
    LocalDateTime endTime;

    /** Tipo de alquiler realizado. */
    RentalType rentalType;

    /**
     * Constructor por defecto.
     * <p>
     * Inicializa:
     * <ul>
     *     <li>{@code startTime} con el momento actual.</li>
     *     <li>{@code endTime} como {@code null}.</li>
     * </ul>
     * Útil para crear alquileres rápidamente desde los servicios.
     */
    public Rental() {
        this.startTime = LocalDateTime.now();
        this.endTime = null;
    }

    /**
     * Constructor alternativo para crear un alquiler con valores establecidos.
     * <p>
     * Nota: {@code endTime} se inicializa como {@code null}
     * y debe asignarse cuando el alquiler se finaliza.
     *
     * @param id ID del alquiler.
     * @param animal Animal alquilado.
     * @param customer Cliente que alquila.
     * @param startTime Momento de inicio.
     * @param rentalType Tipo de alquiler.
     */
    public Rental(int id, Animal animal, Customer customer,
                  LocalDateTime startTime, RentalType rentalType) {
        this.id = id;
        this.animal = animal;
        this.customer = customer;
        this.startTime = startTime;
        this.endTime = null;
        this.rentalType = rentalType;
    }

    /** @return ID del alquiler. */
    public int getId() {
        return id;
    }

    /** @param id Nuevo ID del alquiler. */
    public void setId(int id) {
        this.id = id;
    }

    /** @return Animal asociado al alquiler. */
    public Animal getAnimal() {
        return animal;
    }

    /** @param animal Animal alquilado. */
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    /** @return Cliente que realiza el alquiler. */
    public Customer getCustomer() {
        return customer;
    }

    /** @param customer Cliente asociado al alquiler. */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /** @return Fecha y hora de inicio del alquiler. */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /** @param startTime Define el inicio del alquiler. */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /** @return Fecha y hora de finalización; {@code null} si está activo. */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /** @param endTime Define el momento de finalización del alquiler. */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /** @return Tipo de alquiler realizado. */
    public RentalType getRentalType() {
        return rentalType;
    }

    /** @param rentalType Tipo de alquiler. */
    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", animal=" + animal +
                ", customer=" + customer +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rentalType=" + rentalType +
                '}';
    }
}
