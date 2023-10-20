package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.dto.TrainerScheduleDto;
import com.thezookaycompany.zookayproject.model.entity.TrainerScheduleWeekDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
@EnableJpaRepositories
public interface TrainerScheduleWeekDayRepository extends JpaRepository<TrainerScheduleWeekDays,Integer> {


    @Query("SELECT t FROM TrainerScheduleWeekDays t  where t.trainerSchedule.trainerScheduleId = ?1")
    Set<TrainerScheduleWeekDays> findTrainerScheduleById(int trainerScheduleId);

    // Chúng ta sẽ sử dụng Native SQL Query vì truy vấn này không nằm trong trường hợp sử dụng các phương thức tự động được tạo ra bởi Spring Data JPA.
    @Query(value = "SELECT tswd.id, tswd.Trainer_Schedule_ID, wd.Day_Id " +
            "FROM Trainer_Schedule ts " +
            "INNER JOIN Trainer_Schedule_Week_Days tswd ON ts.Trainer_Schedule_ID = tswd.Trainer_Schedule_ID " +
            "INNER JOIN Week_Days wd ON tswd.Day_Id = wd.Day_Id " +
            "WHERE ts.EmpID = :empId", nativeQuery = true)
    List<Integer[]> findTrainerSchedulesAndDaysByEmpId(@Param("empId") Integer empId);
}
