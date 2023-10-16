package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import com.thezookaycompany.zookayproject.model.entity.TrainerScheduleWeekDays;
import com.thezookaycompany.zookayproject.repositories.TrainerScheduleRepository;
import com.thezookaycompany.zookayproject.services.TrainerScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Service
public class TrainerScheduleServiceImpl implements TrainerScheduleService {

    @Autowired
    private TrainerScheduleRepository trainerScheduleRepository;

    @Override
    public Set<TrainerScheduleWeekDays> getTrainerSchedule(int empId) {
        return trainerScheduleRepository.findTrainerScheduleWeekDaysByTrainerScheduleId(empId);
    }

    @Override
    public TrainerSchedule getTrainerScheduleInfo(int empID) {
        return trainerScheduleRepository.findTrainerScheduleById(empID);
    }
}
