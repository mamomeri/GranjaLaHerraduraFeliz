package com.GranjaLaHerraduraFeliz.Exception;

/**
 * Excepción lanzada cuando se intenta acceder a un alquiler
 * que no existe en el repositorio.
 * <p>
 * Esta excepción se utiliza principalmente en {@code RentalService}
 * al buscar un alquiler por su ID. Si el repositorio devuelve
 * {@code null}, se lanza esta excepción para indicar claramente
 * que la operación no puede continuar.
 *
 * <p>Ejemplos típicos de uso:</p>
 * <ul>
 *     <li>Finalizar un alquiler con un ID inexistente.</li>
 *     <li>Consultar un alquiler que nunca fue registrado.</li>
 * </ul>
 *
 * Extiende {@link RuntimeException} para evitar la obligación
 * de manejo explícito (try/catch), lo cual es común en reglas
 * de negocio que representan errores lógicos.
 *
 * @author Marcos
 * @since 1.0
 */
public class RentalNotFoundException extends RuntimeException {

    /**
     * Crea una excepción con un mensaje descriptivo.
     *
     * @param message Mensaje explicando la causa del error.
     */
    public RentalNotFoundException(String message) {
        super(message);
    }
}
