package com.thezookaycompany.zookayproject.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class WeekDays {

    @Id
    @Column(nullable = false,name = "DayId")
    private Integer DayId ;


    @Column (nullable = false,name = "DayName")
    private String DayName;

    @OneToMany(mappedBy = "weekDays")
    private Set<TrainerScheduleWeekDays> trainerScheduleWeekDaysSet;

    public WeekDays(Integer dayId, String dayName) {
        DayId = dayId;
        DayName = dayName;
    }

    public WeekDays() {
    }

    public Integer getDayId() {
        return DayId;
    }

    public void setDayId(Integer dayId) {
        DayId = dayId;
    }

    public String getDayName() {
        return DayName;
    }

    public void setDayName(String dayName) {
        DayName = dayName;
    }
}
