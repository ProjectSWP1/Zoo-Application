package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.TrainerScheduleDto;
import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import com.thezookaycompany.zookayproject.model.entity.TrainerScheduleWeekDays;

import java.util.Set;

public interface TrainerScheduleService {

    Set<TrainerScheduleWeekDays> getTrainerSchedule(int empId);

    TrainerSchedule getTrainerScheduleInfo(int empId);

    String createTrainerSchedule(TrainerScheduleDto trainerScheduleDto);

    String createWeeklySchedule(TrainerScheduleDto trainerScheduleDto);
}
