package com.GranjaLaHerraduraFeliz.service;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.AnimalStatus;
import com.GranjaLaHerraduraFeliz.model.AnimalType;
import com.GranjaLaHerraduraFeliz.repository.AnimalRepository;

import java.util.List;

public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    /**
     * Crea un nuevo animal con estado AVAILABLE y lo guarda en el repositorio.
     */
    public Animal registerAnimal(String name, AnimalType type) {
        Animal animal = new Animal();
        animal.setName(name);
        animal.setType(type);
        animal.setStatus(AnimalStatus.AVAILABLE);   // siempre entra como disponible

        return animalRepository.save(animal);
    }

    public List<Animal> listAllAnimals() {
        return animalRepository.findAll();
    }

    public List<Animal> listAvailableAnimals() {
        return animalRepository.findByStatus(AnimalStatus.AVAILABLE);
    }
}

