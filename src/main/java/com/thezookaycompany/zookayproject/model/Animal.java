package com.thezookaycompany.zookayproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;


@Entity
public class Animal {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer animalId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double height;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", nullable = false)
    private AnimalSpecies species;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cage_id", nullable = false)
    private Cage cage;

    public Integer getAnimalId() {
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

    public Integer getAge() {
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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(final Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
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
