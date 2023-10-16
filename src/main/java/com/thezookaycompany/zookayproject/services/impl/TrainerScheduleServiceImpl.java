package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import com.thezookaycompany.zookayproject.repositories.TrainerScheduleRepository;
import com.thezookaycompany.zookayproject.services.TrainerScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class TrainerScheduleServiceImpl implements TrainerScheduleService {

    @Autowired
    private TrainerScheduleRepository trainerScheduleRepository;

    @Override
    public TrainerSchedule getTrainerSchedule(int empID) {
        return trainerScheduleRepository.findTrainerScheduleById(empID);
    }
}
