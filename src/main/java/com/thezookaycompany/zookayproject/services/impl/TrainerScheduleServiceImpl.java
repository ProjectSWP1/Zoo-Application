package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.TrainerScheduleDto;
import com.thezookaycompany.zookayproject.model.dto.TrainerScheduleInfo;
import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import com.thezookaycompany.zookayproject.model.entity.TrainerScheduleWeekDays;
import com.thezookaycompany.zookayproject.repositories.*;
import com.thezookaycompany.zookayproject.services.TrainerScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Set<TrainerSchedule> getTrainerScheduleInfo(int empID) {
        return trainerScheduleRepository.findTrainerScheduleById(empID);
    }

    @Override
    public List<TrainerScheduleInfo> getTrainerScheduleByEmpId(int empId) {
        List<Integer[]> queryResult = trainerScheduleWeekDayRepository.findTrainerSchedulesAndDaysByEmpId(empId);
        List<TrainerScheduleInfo> trainerScheduleInfos = new ArrayList<>();

        for (Integer[] result : queryResult) {
            TrainerScheduleInfo info = new TrainerScheduleInfo();
            info.setId(result[0]);
            info.setTrainerScheduleId(result[1]);
            info.setDayId(result[2]);
            trainerScheduleInfos.add(info);
        }
        return trainerScheduleInfos;
    }


    @Override
    public String createTrainerSchedule(TrainerScheduleDto trainerScheduleDto) {

        //bây h khống chế kh cho create 2 lịch của 1 trainer mà có chung ngày
        List<TrainerScheduleInfo> workList = getTrainerScheduleByEmpId(trainerScheduleDto.getEmpId());
        if (isDuplicateSchedule(trainerScheduleDto)) {
            return "The trainer's working day already exists. Please schedule another date!";
        }
        // 1 tuần có 7 ngày :)
        else if (workList.size() == 7) {
            return "Trainer's work schedule is full!";
        }
        //validate data
        if (trainerScheduleRepository.existsById(trainerScheduleDto.getTrainerScheduleId())) {
            return "Schedule's ID is already existed.";
        }
        if (trainerScheduleDto.getDescription().isEmpty() || trainerScheduleDto.getDescription().length() > 255 || trainerScheduleDto.getDescription() == null) {
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

        // Check no co trong database hay chua, neu chua => fail to add
        if (!trainerScheduleRepository.existsById(trainerScheduleDto.getTrainerScheduleId())) {
            return "Trainer Schedule with ID " + trainerScheduleDto.getTrainerScheduleId() + " is failed to add";
        }

        // Lấy trainer schedule ra từ ID vào temp rồi check temp có hay ko nữa
        TrainerSchedule temp = trainerScheduleRepository.findById(trainerScheduleDto.getTrainerScheduleId()).orElse(null);
        if (temp == null) {
            return "Cannot found trainer schedule";
        }

        // save lịch ngày chi tiết
        TrainerScheduleWeekDays trainerScheduleWeekDays = new TrainerScheduleWeekDays
                (trainerScheduleDto.getWeekDaysId(), temp,
                        weekDaysRepository.getReferenceById(trainerScheduleDto.getDayId()));


        trainerScheduleWeekDayRepository.save(trainerScheduleWeekDays);
        return "Assign Schedule successfully";
    }

    // hàm check trùng lịch
    private boolean isDuplicateSchedule(TrainerScheduleDto trainerScheduleDto) {
        //check xem lịch mới update có trùng với lịch nào có sẵn của trainer đó hay ko
        List<TrainerScheduleInfo> workList = getTrainerScheduleByEmpId(trainerScheduleDto.getEmpId());
        if (workList != null && workList.size() > 0 && workList.size() < 7) {
            for (TrainerScheduleInfo t : workList) {
                if (t.getDayId().equals(trainerScheduleDto.getDayId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String updateTrainerSchedule(TrainerScheduleDto trainerScheduleDto) {
        // update ở đây chỉ cho sửa đổi ngày làm việc của trainer và đổi đối tượng làm việc
        // vd: rửa đít cho khỉ đổi từ t2 -> t4
        // các attribute sẽ để readonly: trainerSchduleId, empId, Id (key của TSWD)


        // DayId
        // check trùng lịch mới vs cũ
        if (isDuplicateSchedule(trainerScheduleDto)) {
            return "The trainer's working day already exists. Please schedule another date!";
        }
        if (trainerScheduleDto.getDescription().isEmpty() || trainerScheduleDto.getDescription().length() > 255 || trainerScheduleDto.getDescription() == null) {
            return "Invalid input field: description";
        }
        if (!animalSpeciesRepository.existsById(trainerScheduleDto.getSpeciesId())) {
            return "Animal Species is not found!";
        }
        Set<TrainerScheduleWeekDays> listSchedule = trainerScheduleWeekDayRepository.findTrainerScheduleById(trainerScheduleDto.getTrainerScheduleId());
        if (listSchedule != null && listSchedule.size() > 0) {
            TrainerSchedule trainerSchedule = new TrainerSchedule();
            trainerSchedule.setEmp(employeesRepository.getReferenceById(trainerScheduleDto.getEmpId()));
            trainerSchedule.setTrainerScheduleId(trainerScheduleDto.getTrainerScheduleId());
            trainerSchedule.setDescription(trainerScheduleDto.getDescription());
            trainerSchedule.setSpecies(animalSpeciesRepository.findAnimalSpeciesBySpeciesId(trainerScheduleDto.getSpeciesId()));

            //save trainerSchedule
            trainerScheduleRepository.save(trainerSchedule);

            TrainerScheduleWeekDays trainerScheduleWeekDays = new TrainerScheduleWeekDays();
            trainerScheduleWeekDays.setId(trainerScheduleDto.getWeekDaysId());
            trainerScheduleWeekDays.setWeekDays(weekDaysRepository.getReferenceById(trainerScheduleDto.getDayId()));
            trainerScheduleWeekDays.setTrainerSchedule(trainerSchedule);

            // save scheduleWeekdays
            trainerScheduleWeekDayRepository.save(trainerScheduleWeekDays);
            return "Update Schedule successfully!";
        }
        return "Employee has no Schedule to update. Go create ones";
    }

    @Override
    public String removeTrainerSchedule(Integer empId, Integer trainerScheduleId) {
        List<TrainerScheduleInfo> workList = getTrainerScheduleByEmpId(empId);
        if (workList != null && workList.size() > 0) {
            for (TrainerScheduleInfo t : workList) {
                //check extist
                if (t.getTrainerScheduleId().equals(trainerScheduleId)) {
                    trainerScheduleWeekDayRepository.deleteById(t.getId());
                    trainerScheduleRepository.deleteById(trainerScheduleId);
                    return "Trainer's Schedule removed successfully!";
                }
            }
        }
        return "Trainer's Schedule not found.";
    }

}
