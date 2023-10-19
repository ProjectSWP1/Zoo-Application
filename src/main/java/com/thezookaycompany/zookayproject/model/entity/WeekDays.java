package com.thezookaycompany.zookayproject.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class WeekDays {

    @Id
    @Column(nullable = false,name = "DayId")
    private Integer DayId;


    @Column (nullable = false,name = "DayName")
    private String DayName;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "DayId")
    private Set<TrainerScheduleWeekDays> daysInTrainerSchedule;



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
