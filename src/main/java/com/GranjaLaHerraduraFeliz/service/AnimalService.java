package com.GranjaLaHerraduraFeliz.service;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.AnimalStatus;
import com.GranjaLaHerraduraFeliz.model.AnimalType;
import com.GranjaLaHerraduraFeliz.repository.AnimalRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los animales.
 * <p>
 * Funciones principales:
 * <ul>
 *     <li>Registrar nuevos animales en la granja.</li>
 *     <li>Listar todos los animales registrados.</li>
 *     <li>Listar únicamente los animales disponibles para alquiler.</li>
 * </ul>
 *
 * Esta capa abstrae al controlador de los detalles del repositorio,
 * permitiendo aplicar reglas del negocio sin mezclar lógica de persistencia.
 *
 * @author Marcos
 * @since 1.0 (versión consola)
 */
public class AnimalService {

    // Logger para registrar eventos de negocio y diagnósticos
    private static final Logger log = LoggerFactory.getLogger(AnimalService.class);

    private final AnimalRepository animalRepository;

    /**
     * Crea una instancia del servicio inyectando el repositorio necesario.
     *
     * @param animalRepository Repositorio encargado de almacenar y gestionar animales.
     */
    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;

        log.info("AnimalService inicializado.");
        // Comentario: informa en despliegues / contenedores que el servicio está operativo.
    }

    /**
     * Registra un nuevo animal con estado inicial {@link AnimalStatus#AVAILABLE}.
     * <p>
     * Reglas del negocio:
     * <ul>
     *     <li>Todo animal nuevo entra como disponible.</li>
     *     <li>La asignación del ID queda a cargo del repositorio.</li>
     * </ul>
     *
     * @param name Nombre del animal.
     * @param type Tipo de animal, definido en {@link AnimalType}.
     * @return Animal registrado y persistido.
     */
    public Animal registerAnimal(String name, AnimalType type) {

        log.debug("Registrando animal. Nombre='{}', Tipo={}", name, type);
        // Comentario: DEBUG → seguimiento detallado del flujo para depurar.

        Animal animal = new Animal();
        animal.setName(name);
        animal.setType(type);
        animal.setStatus(AnimalStatus.AVAILABLE);

        Animal saved = animalRepository.save(animal);

        log.info("Animal registrado: ID={}, Nombre='{}', Tipo={}, Estado={}",
                saved.getId(), saved.getName(), saved.getType(), saved.getStatus());
        // Comentario: INFO → registra un evento importante del negocio.

        return saved;
    }

    /**
     * Obtiene una lista con todos los animales registrados.
     *
     * @return Lista completa de {@link Animal}.
     */
    public List<Animal> listAllAnimals() {

        log.debug("Consultando la lista completa de animales.");
        // Comentario: DEBUG → operación de lectura que puede ser monitoreada.

        List<Animal> animals = animalRepository.findAll();

        log.info("Consulta completada. Total de animales registrados: {}", animals.size());
        // Comentario: INFO → útil para auditoría y monitoreo del sistema.

        return animals;
    }

    /**
     * Lista únicamente los animales cuyo estado es {@link AnimalStatus#AVAILABLE}.
     *
     * @return Lista de animales disponibles.
     */
    public List<Animal> listAvailableAnimals() {

        log.debug("Consultando animales disponibles (estado AVAILABLE).");
        // Comentario: DEBUG → detalla consultas filtradas útiles para diagnósticos.

        List<Animal> available = animalRepository.findByStatus(AnimalStatus.AVAILABLE);

        log.info("Animales disponibles encontrados: {}", available.size());
        // Comentario: INFO → buen indicador de capacidad disponible del sistema.

        return available;
    }
}
