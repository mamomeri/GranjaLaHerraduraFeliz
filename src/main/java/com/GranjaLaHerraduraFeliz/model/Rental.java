package com.GranjaLaHerraduraFeliz.model;

import java.time.LocalDateTime;

public class Rental {
    int id;
    Animal animal;
    Customer customer;
    LocalDateTime startTime;
    LocalDateTime endTime;
    RentalType rentalType;

    public Rental() {
        this.startTime = LocalDateTime.now();
        this.endTime = null;
    }

    public Rental(int id, Animal animal, Customer customer, LocalDateTime startTime, RentalType rentalType) {
        this.id = id;
        this.animal = animal;
        this.customer = customer;

        // Para alquileres de demostración, endTime lo puedes asignar después
        this.endTime = null;

        this.rentalType = rentalType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", animal=" + animal +
                ", customer=" + customer +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rentalType=" + rentalType +
                '}';
    }
}
