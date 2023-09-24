package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.*;

@Entity
public class Animal {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int animalId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double height;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SpeciesID", nullable = false)
    private AnimalSpecies species;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CageID", nullable = false)
    private Cage cage;

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(final Integer animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(final Double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(final Double height) {
        this.height = height;
    }

    public AnimalSpecies getSpecies() {
        return species;
    }

    public void setSpecies(final AnimalSpecies species) {
        this.species = species;
    }

    public Cage getCage() {
        return cage;
    }

    public void setCage(final Cage cage) {
        this.cage = cage;
    }

}