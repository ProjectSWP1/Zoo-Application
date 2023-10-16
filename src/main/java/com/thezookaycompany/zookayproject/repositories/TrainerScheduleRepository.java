package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerScheduleRepository extends JpaRepository<TrainerSchedule, Integer> {

    // hàm trả về thời gian làm việc của 1 cái trainerSchedule
    @Query("SELECT a FROM TrainerSchedule a where a.emp.empId = ?1")
    TrainerSchedule findTrainerScheduleById(int empId);

}
