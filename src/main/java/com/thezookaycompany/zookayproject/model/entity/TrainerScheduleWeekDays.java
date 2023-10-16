package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.*;

@Entity
public class TrainerScheduleWeekDays {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainerScheduleID")
    private TrainerSchedule trainerSchedule;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dayId")
    private WeekDays weekDays;

    public TrainerScheduleWeekDays(TrainerSchedule trainerSchedule, WeekDays weekDays) {
        this.trainerSchedule = trainerSchedule;
        this.weekDays = weekDays;
    }

    public TrainerScheduleWeekDays() {

    }

    public TrainerSchedule getTrainerSchedule() {
        return trainerSchedule;
    }

    public void setTrainerSchedule(TrainerSchedule trainerSchedule) {
        this.trainerSchedule = trainerSchedule;
    }

    public WeekDays getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(WeekDays weekDays) {
        this.weekDays = weekDays;
    }
}
