package com.GranjaLaHerraduraFeliz.repository;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.AnimalStatus;

import java.util.List;

public interface AnimalRepository {
    Animal save(Animal animal);
    Animal findById(int id);
    List<Animal> findAll();
    List<Animal> findByStatus(AnimalStatus status);
}
