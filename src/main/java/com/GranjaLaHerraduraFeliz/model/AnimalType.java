package com.GranjaLaHerraduraFeliz.model;

/**
 * Representa los tipos de animales disponibles en la granja.
 * <p>
 * Cada tipo define una categoría general de animal que puede ser alquilado.
 *
 * <ul>
 *     <li>{@link #HORSE}: Caballo.</li>
 *     <li>{@link #DONKEY}: Burro.</li>
 *     <li>{@link #PIG}: Cerdito (incluye tipos usados para paseos especiales o infantiles).</li>
 * </ul>
 *
 * Este enum permite clasificar animales y simplificar filtros,
 * búsquedas y reglas del negocio.
 */
public enum AnimalType {
    HORSE,
    DONKEY,
    PIG
}
