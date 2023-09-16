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
public class FeedingSchedule {

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
    private Integer feedScheduleId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private AnimalFood food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", nullable = false)
    private AnimalSpecies species;

    public Integer getFeedScheduleId() {
        return feedScheduleId;
    }

    public void setFeedScheduleId(final Integer feedScheduleId) {
        this.feedScheduleId = feedScheduleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public AnimalFood getFood() {
        return food;
    }

    public void setFood(final AnimalFood food) {
        this.food = food;
    }

    public AnimalSpecies getSpecies() {
        return species;
    }

    public void setSpecies(final AnimalSpecies species) {
        this.species = species;
    }

}
