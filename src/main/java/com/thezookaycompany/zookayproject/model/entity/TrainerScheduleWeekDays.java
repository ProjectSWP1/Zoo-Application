package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.*;

@Entity
public class TrainerScheduleWeekDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerScheduleId")
    private TrainerSchedule trainerSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DayId")
    private WeekDays weekDays;

    public TrainerScheduleWeekDays() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public TrainerScheduleWeekDays(Integer id, TrainerSchedule trainerSchedule, WeekDays weekDays) {
        this.id = id;
        this.trainerSchedule = trainerSchedule;
        this.weekDays = weekDays;
    }

    public TrainerScheduleWeekDays(TrainerSchedule trainerSchedule, WeekDays weekDays) {
        this.trainerSchedule = trainerSchedule;
        this.weekDays = weekDays;
    }
}
