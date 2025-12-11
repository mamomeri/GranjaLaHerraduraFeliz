package com.GranjaLaHerraduraFeliz.repository;

import com.GranjaLaHerraduraFeliz.model.Customer;

import java.util.List;

/**
 * Repositorio encargado de gestionar la persistencia de objetos {@link Customer}.
 * <p>
 * Define las operaciones fundamentales necesarias para:
 * <ul>
 *     <li>Guardar o actualizar clientes.</li>
 *     <li>Buscar clientes por ID.</li>
 *     <li>Obtener la lista completa de clientes registrados.</li>
 * </ul>
 *
 * Esta interfaz permite desacoplar la lógica de negocio de la forma en que
 * se almacenan los datos. Implementaciones pueden variar entre almacenamiento
 * en memoria, base de datos relacional, archivos o servicios externos, sin
 * modificar el código de los servicios que la utilizan.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public interface CustomerRepository {

    /**
     * Guarda un cliente en el repositorio.
     * <p>
     * Dependiendo de la implementación:
     * <ul>
     *     <li>Si el cliente no tiene ID asignado, debe generarse uno nuevo.</li>
     *     <li>Si ya tiene ID, se actualizará el registro correspondiente.</li>
     * </ul>
     *
     * @param customer Cliente a guardar o actualizar.
     * @return El cliente guardado.
     */
    Customer save(Customer customer);

    /**
     * Busca un cliente por su ID.
     *
     * @param id Identificador único del cliente.
     * @return El cliente encontrado o {@code null} si no existe.
     */
    Customer findById(int id);

    /**
     * Obtiene una lista completa de clientes registrados.
     *
     * @return Lista de {@link Customer}.
     */
    List<Customer> findAll();
}
