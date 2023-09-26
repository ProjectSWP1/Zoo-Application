package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Set;


@Entity
public class Cage {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cageID;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZooAreaID", nullable = false)
    private ZooArea zooArea;

    @OneToMany(mappedBy = "cage")
    private Set<Animal> cageAnimals;

    public Integer getCageID() {
        return cageID;
    }

    public void setCageID(final Integer cageID) {
        this.cageID = cageID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
    }

    public ZooArea getZooArea() {
        return zooArea;
    }

    public void setZooArea(final ZooArea zooArea) {
        this.zooArea = zooArea;
    }

    public Set<Animal> getCageAnimals() {
        return cageAnimals;
    }

    public void setCageAnimals(final Set<Animal> cageAnimals) {
        this.cageAnimals = cageAnimals;
    }

}