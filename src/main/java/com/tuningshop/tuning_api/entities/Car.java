package com.tuningshop.tuning_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private Integer baseHorsepower;

    // Construtor vazio exigido pelo JPA
    public Car() {
    }

    // Construtor com todos os argumentos para facilitar a criação do objeto
    public Car(Long id, String brand, String model, Integer baseHorsepower) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.baseHorsepower = baseHorsepower;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getBaseHorsepower() {
        return baseHorsepower;
    }

    public void setBaseHorsepower(Integer baseHorsepower) {
        this.baseHorsepower = baseHorsepower;
    }

    // equals e hashCode focados no ID (Clean Code / Boas práticas em Entidades JPA)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
