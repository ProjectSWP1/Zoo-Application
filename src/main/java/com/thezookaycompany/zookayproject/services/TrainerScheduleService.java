package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.TrainerScheduleDto;
import com.thezookaycompany.zookayproject.model.dto.TrainerScheduleInfo;
import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import com.thezookaycompany.zookayproject.model.entity.TrainerScheduleWeekDays;

import java.util.List;
import java.util.Set;

public interface TrainerScheduleService {

    Set<TrainerScheduleWeekDays> getTrainerSchedule(int empId);

    Set<TrainerSchedule> getTrainerScheduleInfo(int empId);

    List<TrainerScheduleInfo> getTrainerScheduleByEmpId (int empId);

    String createTrainerSchedule(TrainerScheduleDto trainerScheduleDto);

    String updateTrainerSchedule(TrainerScheduleDto trainerScheduleDto);

    String removeTrainerSchedule(Integer empId, Integer trainerScheduleId);
}
