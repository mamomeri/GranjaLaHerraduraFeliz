package com.GranjaLaHerraduraFeliz.repository;

import com.GranjaLaHerraduraFeliz.model.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación en memoria del repositorio de {@link Customer}.
 * <p>
 * Esta clase almacena los clientes en una lista local, permitiendo simular
 * una persistencia básica sin necesidad de una base de datos. Es ideal para
 * prototipos, pruebas iniciales y la versión de consola del sistema.
 * <p>
 * Características principales:
 * <ul>
 *     <li>Asigna IDs incrementales automáticamente.</li>
 *     <li>Permite registrar y actualizar clientes.</li>
 *     <li>Provee métodos para buscar por ID y obtener la lista completa.</li>
 *     <li>No persiste datos entre ejecuciones (almacenamiento volátil).</li>
 * </ul>
 *
 * Esta implementación puede reemplazarse más adelante por una basada en BD
 * sin modificar los servicios, gracias a la interfaz {@link CustomerRepository}.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public class InMemoryCustomerRepository implements CustomerRepository {

    /** Lista interna donde se almacenan los clientes registrados. */
    private final List<Customer> customers = new ArrayList<>();

    /** Contador que genera IDs incrementales automáticamente. */
    private int nextId = 1;

    /**
     * Guarda un cliente en el repositorio.
     * <p>
     * Reglas:
     * <ul>
     *     <li>Si el cliente tiene ID 0, se trata como registro nuevo y se le asigna un ID.</li>
     *     <li>Si el ID ya existe, se actualiza el registro correspondiente.</li>
     *     <li>Si el ID no existe en la lista, se agrega como nuevo.</li>
     * </ul>
     *
     * @param customer Cliente a registrar o actualizar.
     * @return El cliente guardado.
     */
    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == 0) {
            customer.setId(nextId++);
            customers.add(customer);
        } else {
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getId() == customer.getId()) {
                    customers.set(i, customer);
                    return customer;
                }
            }
            customers.add(customer);
        }
        return customer;
    }

    /**
     * Busca un cliente por su ID.
     *
     * @param id Identificador único del cliente.
     * @return El cliente encontrado o {@code null} si no existe.
     */
    @Override
    public Customer findById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Obtiene una lista completa de los clientes registrados.
     * <p>
     * Se devuelve una copia defensiva de la lista para evitar modificaciones directas.
     *
     * @return Lista de {@link Customer}.
     */
    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }
}
