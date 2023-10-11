package com.thezookaycompany.zookayproject.model.dto;

import com.thezookaycompany.zookayproject.model.entity.AnimalSpecies;
import com.thezookaycompany.zookayproject.model.entity.Cage;

public class AnimalDto {

    private int animalId;
    private String name;
    private String description;
    private int age;
    private String gender;
    private double weight;
    private double height;
    private String speciesName; // Assuming you want to include species name in the DTO
    private String cageName; // Assuming you want to include cage name in the DTO
    private String cageID;
    private Integer speciesId;
    private String groups;
    private AnimalSpecies animalSpecies;
    private Cage cage;

    // Constructors, getters, and setters

    public AnimalSpecies getAnimalSpecies() {
        return animalSpecies;
    }

    public void setAnimalSpecies(AnimalSpecies animalSpecies) {
        this.animalSpecies = animalSpecies;
    }

    public Cage getCage() {
        return cage;
    }

    public void setCage(Cage cage) {
        this.cage = cage;
    }

    public AnimalDto(String speciesName, int speciesId, String groups) {
        this.speciesName = speciesName;
        this.speciesId = speciesId;
        this.groups = groups;
    }

    public AnimalDto() {
        // Default constructor
    }


    public AnimalDto(int animalId, String name, String description, int age, String gender, double weight, double height, String speciesName, String cageName) {
        this.animalId = animalId;
        this.name = name;
        this.description = description;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.speciesName = speciesName;
        this.cageName = cageName;
    }

    public AnimalDto(int animalId, String name, String description, int age, String gender, double weight, double height, String cageID, Integer speciesId) {
        this.animalId = animalId;
        this.name = name;
        this.description = description;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.cageID = cageID;
        this.speciesId = speciesId;
    }
// Getter and setter methods for each field

    public String getCageID() {
        return cageID;
    }

    public void setCageID(String cageID) {
        this.cageID = cageID;
    }

    public Integer getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Integer speciesId) {
        this.speciesId = speciesId;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getCageName() {
        return cageName;
    }

    public void setCageName(String cageName) {
        this.cageName = cageName;
    }
}
