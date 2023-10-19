package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Entity
@EnableJpaRepositories
public class TrainerScheduleWeekDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerScheduleId")
    private TrainerSchedule trainerSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DayId")
    private WeekDays weekDays;

    public TrainerScheduleWeekDays() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public TrainerScheduleWeekDays(Long id, TrainerSchedule trainerSchedule, WeekDays weekDays) {
        this.id = id;
        this.trainerSchedule = trainerSchedule;
        this.weekDays = weekDays;
    }

    public TrainerScheduleWeekDays(TrainerSchedule trainerSchedule, WeekDays weekDays) {
        this.trainerSchedule = trainerSchedule;
        this.weekDays = weekDays;
    }
}
