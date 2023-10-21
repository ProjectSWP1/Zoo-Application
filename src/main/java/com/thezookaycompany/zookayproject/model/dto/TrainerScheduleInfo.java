package com.thezookaycompany.zookayproject.model.dto;

public class TrainerScheduleInfo {
    private Integer id;
    private Integer trainerScheduleId;
    private Integer dayId;

    public TrainerScheduleInfo() {
    }

    public TrainerScheduleInfo(Integer id, Integer trainerScheduleId, Integer dayId) {
        this.id = id;
        this.trainerScheduleId = trainerScheduleId;
        this.dayId = dayId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainerScheduleId() {
        return trainerScheduleId;
    }

    public void setTrainerScheduleId(Integer trainerScheduleId) {
        this.trainerScheduleId = trainerScheduleId;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }
}

