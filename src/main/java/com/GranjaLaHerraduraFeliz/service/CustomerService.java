package com.GranjaLaHerraduraFeliz.service;

import com.GranjaLaHerraduraFeliz.model.Customer;
import com.GranjaLaHerraduraFeliz.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    // Logger del servicio (registrará eventos importantes del flujo de negocio)
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    /**
     * Construye un servicio de clientes inyectando un repositorio.
     *
     * @param customerRepository Repositorio que administra los datos de clientes.
     */
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        log.info("CustomerService inicializado.");
        // Comentario: Log útil para saber cuándo el servicio se construyó (útil en despliegues PaaS)
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

        log.debug("Intentando registrar cliente con nombre: '{}'", name);
        // Comentario: DEBUG → seguimiento detallado de flujo, no se muestra en producción.

        Customer customer = new Customer();
        customer.setFullName(name);

        Customer saved = customerRepository.save(customer);

        log.info("Cliente registrado con ID {} y nombre '{}'", saved.getId(), saved.getFullName());
        // Comentario: INFO → evento de negocio exitoso.

        return saved;
    }

    /**
     * Obtiene un listado de todos los clientes registrados.
     *
     * @return Lista de objetos {@link Customer}.
     */
    public List<Customer> listAllCustomers() {

        log.debug("Consultando todos los clientes registrados.");
        // Comentario: DEBUG → seguimiento de consultas del sistema.

        List<Customer> customers = customerRepository.findAll();

        log.info("Se encontraron {} clientes registrados.", customers.size());
        // Comentario: INFO → resumen de la operación para monitoreo.

        return customers;
    }
}
