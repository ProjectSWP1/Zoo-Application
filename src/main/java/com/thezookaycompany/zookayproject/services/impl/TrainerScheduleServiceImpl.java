package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.TrainerScheduleDto;
import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import com.thezookaycompany.zookayproject.model.entity.TrainerScheduleWeekDays;
import com.thezookaycompany.zookayproject.repositories.*;
import com.thezookaycompany.zookayproject.services.TrainerScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TrainerScheduleServiceImpl implements TrainerScheduleService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private AnimalSpeciesRepository animalSpeciesRepository;

    @Autowired
    private WeekDaysRepository weekDaysRepository;

    @Autowired
    private TrainerScheduleWeekDayRepository trainerScheduleWeekDayRepository;

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



    @Override
    public String CreateTrainerSchedule(TrainerScheduleDto trainerScheduleDto) {
        //validate data
        if(trainerScheduleRepository.existsById(trainerScheduleDto.getTrainerScheduleId())){
            return "Schedule's ID is already existed.";
        }
        if (trainerScheduleDto.getDescription().isEmpty() || trainerScheduleDto.getDescription().length() >255 || trainerScheduleDto.getDescription() ==null){
            return "Invalid input field: description";
        }
    // species, empId, DayId fe sẽ handle chuẩn data nên kh cần validate lại
        // khởi tạo trainer Schedule
        TrainerSchedule trainerSchedule = new TrainerSchedule();
        trainerSchedule.setTrainerScheduleId(trainerScheduleDto.getTrainerScheduleId());
        trainerSchedule.setDescription(trainerScheduleDto.getDescription());
        trainerSchedule.setEmp(employeesRepository.getReferenceById(trainerScheduleDto.getEmpId()));
        trainerSchedule.setSpecies(animalSpeciesRepository.getReferenceById(trainerScheduleDto.getSpeciesId()));

        //save trainer schedule vừa tạo trước để weekdaysSchedule có thể trỏ vào
        trainerScheduleRepository.save(trainerSchedule);

        //khởi tạo schedule weekday để hoàn thiện trainer schedule
        TrainerScheduleWeekDays trainerScheduleWeekDays = new TrainerScheduleWeekDays
                (trainerScheduleDto.getWeekDaysId(),trainerScheduleRepository.getReferenceById(trainerScheduleDto.getTrainerScheduleId()),
                        weekDaysRepository.getReferenceById(trainerScheduleDto.getDayId()));

        // save
        //phải save thg WeekDays trc vì trainerSchedule chứa cả WeekDays
        trainerScheduleWeekDayRepository.save(trainerScheduleWeekDays);



        return "Assign Schedule successfully";
    }

    @Override
    public String createWeeklySchedule(TrainerScheduleDto trainerScheduleDto) {
        TrainerScheduleWeekDays trainerScheduleWeekDays = new TrainerScheduleWeekDays
                (trainerScheduleDto.getWeekDaysId(),trainerScheduleRepository.getReferenceById(trainerScheduleDto.getTrainerScheduleId()),
                        weekDaysRepository.getReferenceById(trainerScheduleDto.getDayId()));
        trainerScheduleWeekDayRepository.save(trainerScheduleWeekDays);
        return "200 OK";
    }
}
