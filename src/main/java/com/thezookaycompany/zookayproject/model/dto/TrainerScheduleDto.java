package com.thezookaycompany.zookayproject.model.dto;

public class TrainerScheduleDto {

    private Integer trainerScheduleId;
    private String description;
    private Integer empId;
    private Integer speciesId;
    private Integer dayId;

    private Integer weekDaysId;

    public Integer getWeekDaysId() {
        return weekDaysId;
    }

    public void setWeekDaysId(Integer weekDaysId) {
        this.weekDaysId = weekDaysId;
    }

    public TrainerScheduleDto(Integer trainerScheduleId, String description, Integer empId, Integer speciesId, Integer dayId, Integer weekDaysId) {
        this.trainerScheduleId = trainerScheduleId;
        this.description = description;
        this.empId = empId;
        this.speciesId = speciesId;
        this.dayId = dayId;
        this.weekDaysId=weekDaysId;
    }

    public TrainerScheduleDto() {
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    public Integer getTrainerScheduleId() {
        return trainerScheduleId;
    }

    public void setTrainerScheduleId(Integer trainerScheduleId) {
        this.trainerScheduleId = trainerScheduleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Integer speciesId) {
        this.speciesId = speciesId;
    }
}
