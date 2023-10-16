package com.thezookaycompany.zookayproject.model.entity;

public class TrainerScheduleDto {

    private int empId;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public TrainerScheduleDto(int empId) {
        this.empId = empId;
    }
}