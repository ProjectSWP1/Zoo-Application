package com.thezookaycompany.zookayproject.model.dto;

import com.thezookaycompany.zookayproject.model.entity.Animal;
import com.thezookaycompany.zookayproject.model.entity.AnimalSpecies;
import com.thezookaycompany.zookayproject.model.entity.Cage;

import java.util.List;

public class AnimalResponse {
    private List<Animal> animals;


    public AnimalResponse() {
        super();
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }


    public AnimalResponse(List<Animal> animals) {
        this.animals = animals;
    }

}
