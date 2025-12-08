package com.GranjaLaHerraduraFeliz.repository;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.AnimalStatus;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAnimalRepository implements AnimalRepository {

    private final List<Animal> animals = new ArrayList<>();
    private int nextId = 1; // autoincremento simple en memoria

    @Override
    public Animal save(Animal animal) {
        if (animal.getId() == 0) {
            // nuevo animal
            animal.setId(nextId++);
            animals.add(animal);
        } else {
            // actualización: reemplazar el existente
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i).getId() == animal.getId()) {
                    animals.set(i, animal);
                    return animal;
                }
            }
            // si no estaba, lo agregamos igual
            animals.add(animal);
        }
        return animal;
    }

    @Override
    public Animal findById(int id) {
        for (Animal animal : animals) {
            if (animal.getId() == id) {
                return animal;
            }
        }
        return null; // el servicio decidirá qué hacer si es null
    }

    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(animals); // copia defensiva
    }

    @Override
    public List<Animal> findByStatus(AnimalStatus status) {
        List<Animal> result = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.getStatus() == status) {
                result.add(animal);
            }
        }
        return result;
    }
}
