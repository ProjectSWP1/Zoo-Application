package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import com.thezookaycompany.zookayproject.model.entity.TrainerScheduleWeekDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerScheduleRepository extends JpaRepository<TrainerSchedule, Integer> {

    // hàm trả về list thời gian làm việc trong tuần của 1 cái trainerSchedule
    @Query("SELECT a FROM TrainerScheduleWeekDays a inner join a.trainerSchedule r where r.trainerScheduleId = ?1")
    List<TrainerScheduleWeekDays> findTrainerScheduleById(@Param("trainerSchedule") String trainerScheduleId);

}
