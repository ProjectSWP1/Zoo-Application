package com.thezookaycompany.zookayproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.Set;


@Entity
public class AnimalFood {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer foodId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 50)
    private String origin;

    @Column(nullable = false)
    private LocalDate importDate;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "food")
    private Set<FeedingSchedule> foodFeedingSchedules;

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(final Integer foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(final LocalDate importDate) {
        this.importDate = importDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<FeedingSchedule> getFoodFeedingSchedules() {
        return foodFeedingSchedules;
    }

    public void setFoodFeedingSchedules(final Set<FeedingSchedule> foodFeedingSchedules) {
        this.foodFeedingSchedules = foodFeedingSchedules;
    }

}
