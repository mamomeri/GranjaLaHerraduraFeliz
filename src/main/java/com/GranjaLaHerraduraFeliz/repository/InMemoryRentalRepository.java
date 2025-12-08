package com.GranjaLaHerraduraFeliz.repository;

import com.GranjaLaHerraduraFeliz.model.Animal;
import com.GranjaLaHerraduraFeliz.model.Rental;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRentalRepository implements RentalRepository {

    private final List<Rental> rentals = new ArrayList<>();
    private int nextId = 1;

    @Override
    public Rental save(Rental rental) {
        if (rental.getId() == 0) {
            rental.setId(nextId++);
            rentals.add(rental);
        } else {
            for (int i = 0; i < rentals.size(); i++) {
                if (rentals.get(i).getId() == rental.getId()) {
                    rentals.set(i, rental);
                    return rental;
                }
            }
            rentals.add(rental);
        }
        return rental;
    }

    @Override
    public Rental findById(int id) {
        for (Rental rental : rentals) {
            if (rental.getId() == id) {
                return rental;
            }
        }
        return null;
    }

    @Override
    public List<Rental> findAll() {
        return new ArrayList<>(rentals);
    }

    @Override
    public List<Rental> findActiveByAnimal(Animal animal) {
        List<Rental> result = new ArrayList<>();
        for (Rental rental : rentals) {
            boolean sameAnimal = rental.getAnimal().getId() == animal.getId();
            boolean active = rental.getEndTime() == null; // activo si no se ha cerrado
            if (sameAnimal && active) {
                result.add(rental);
            }
        }
        return result;
    }
}
