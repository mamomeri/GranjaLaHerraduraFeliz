package com.GranjaLaHerraduraFeliz.repository;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.Rental;

import java.util.List;

public interface RentalRepository {
    Rental save(Rental rental);
    Rental findById(int id);
    List<Rental> findAll();
    List<Rental> findActiveByAnimal(Animal animal);
}
