package com.GranjaLaHerraduraFeliz.model;

/**
 * Representa el estado actual de un animal dentro del sistema.
 * <p>
 * Los estados determinan si un animal puede ser alquilado o si ya se encuentra en uso.
 *
 * <ul>
 *     <li>{@link #AVAILABLE}: El animal está disponible para alquiler.</li>
 *     <li>{@link #RENTED}: El animal está actualmente alquilado y no puede ser usado.</li>
 * </ul>
 *
 * Este enum es fundamental para las validaciones en la capa de servicios,
 * especialmente en {@code RentalService}.
 */
public enum AnimalStatus {
    AVAILABLE,
    RENTED
}
