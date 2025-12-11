package com.GranjaLaHerraduraFeliz.service;

import com.GranjaLaHerraduraFeliz.model.Customer;
import com.GranjaLaHerraduraFeliz.repository.CustomerRepository;

import java.util.List;

/**
 * Servicio encargado de manejar la lógica de negocio relacionada con los clientes.
 * <p>
 * Funciones principales:
 * <ul>
 *     <li>Registrar nuevos clientes.</li>
 *     <li>Listar todos los clientes del sistema.</li>
 * </ul>
 *
 * Este servicio sirve como capa intermedia entre el controlador y el repositorio,
 * manteniendo la lógica de negocio separada de la persistencia.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Construye un servicio de clientes inyectando un repositorio.
     *
     * @param customerRepository Repositorio que administra los datos de clientes.
     */
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Registra un nuevo cliente en el sistema.
     * <p>
     * Reglas del negocio:
     * <ul>
     *     <li>El nombre no debe ser nulo o vacío (opcional: validación futura).</li>
     *     <li>El ID es asignado automáticamente por el repositorio.</li>
     * </ul>
     *
     * @param name Nombre completo del cliente.
     * @return Cliente registrado y almacenado en el repositorio.
     */
    public Customer registerCustomer(String name) {
        Customer customer = new Customer();
        customer.setFullName(name);

        return customerRepository.save(customer);
    }

    /**
     * Obtiene un listado de todos los clientes registrados.
     *
     * @return Lista de objetos {@link Customer}.
     */
    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }
}
