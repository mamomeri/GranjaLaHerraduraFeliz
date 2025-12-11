package com.GranjaLaHerraduraFeliz.service;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.AnimalStatus;
import com.GranjaLaHerraduraFeliz.model.AnimalType;
import com.GranjaLaHerraduraFeliz.repository.AnimalRepository;
import com.GranjaLaHerraduraFeliz.repository.InMemoryAnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link AnimalService}.
 * <p>
 * Simula el registro de animales típicos de la granja
 * y el filtrado por disponibilidad.
 */
public class AnimalServiceTest {

    private AnimalRepository animalRepository;
    private AnimalService animalService;

    @BeforeEach
    void setUp() {
        animalRepository = new InMemoryAnimalRepository();
        animalService = new AnimalService(animalRepository);
    }

    @Test
    void registerAnimal_assignsIdAndSetsAvailableStatus() {
        // Act
        Animal animal = animalService.registerAnimal("Pony Chispita", AnimalType.HORSE);

        // Assert
        assertTrue(animal.getId() > 0, "El animal debería tener un ID generado");
        assertEquals("Pony Chispita", animal.getName());
        assertEquals(AnimalType.HORSE, animal.getType());
        assertEquals(AnimalStatus.AVAILABLE, animal.getStatus());
    }

    @Test
    void listAvailableAnimals_returnsOnlyAvailableOnes() {
        // Arrange: 2 disponibles, 1 alquilado
        Animal a1 = animalService.registerAnimal("Pegaso", AnimalType.HORSE);
        Animal a2 = animalService.registerAnimal("Manchitas", AnimalType.PIG);
        Animal a3 = animalService.registerAnimal("Burro Lento", AnimalType.DONKEY);

        // Marcamos uno como RENTED manualmente (como si estuviera alquilado)
        a3.setStatus(AnimalStatus.RENTED);
        animalRepository.save(a3);

        // Act
        List<Animal> available = animalService.listAvailableAnimals();

        // Assert
        assertEquals(2, available.size(), "Solo deberían retornar los animales disponibles");
        assertTrue(available.stream().noneMatch(a -> a.getStatus() == AnimalStatus.RENTED),
                "No debería haber animales RENTED en la lista de disponibles");
    }

    @Test
    void listAllAnimals_returnsAllRegisteredAnimals() {
        // Arrange
        animalService.registerAnimal("Trueno", AnimalType.HORSE);
        animalService.registerAnimal("Nube", AnimalType.PIG);

        // Act
        List<Animal> all = animalService.listAllAnimals();

        // Assert
        assertEquals(2, all.size(), "Debería listar todos los animales registrados");
    }
}
