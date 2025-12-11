package com.GranjaLaHerraduraFeliz.Exception;

/**
 * Excepción lanzada cuando se intenta alquilar un animal
 * que no se encuentra disponible.
 * <p>
 * Esta condición ocurre típicamente cuando:
 * <ul>
 *     <li>El animal ya está alquilado por otro cliente.</li>
 *     <li>El estado del animal no es {@code AVAILABLE}.</li>
 * </ul>
 *
 * Se utiliza principalmente en {@code RentalService} dentro del método
 * {@code startRental}, donde es necesario validar la disponibilidad del
 * animal antes de permitir crear un nuevo alquiler.
 *
 * <p>
 * Extiende {@link RuntimeException} para indicar que representa un error
 * lógico del negocio, sin requerir manejo obligatorio con bloques try/catch.
 * </p>
 *
 * @author Marcos
 * @since 1.0
 */
public class AnimalNotAvailableException extends RuntimeException {

    /**
     * Crea una nueva excepción con el mensaje proporcionado.
     *
     * @param message Descripción del motivo por el cual el animal no está disponible.
     */
    public AnimalNotAvailableException(String message) {
        super(message);
    }
}
