package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import com.thezookaycompany.zookayproject.model.entity.TrainerScheduleWeekDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TrainerScheduleRepository extends JpaRepository<TrainerSchedule, Integer> {

    // hàm trả về thời gian làm việc của 1 cái trainerSchedule
    @Query("SELECT t FROM TrainerSchedule t  where t.emp.empId = ?1")
    TrainerSchedule findTrainerScheduleById(int empId);


    @Query("SELECT t.trainerScheduleDays FROM TrainerSchedule t WHERE t.emp.empId = ?1")
    Set<TrainerScheduleWeekDays> findTrainerScheduleWeekDaysByTrainerScheduleId(int empId);

}
