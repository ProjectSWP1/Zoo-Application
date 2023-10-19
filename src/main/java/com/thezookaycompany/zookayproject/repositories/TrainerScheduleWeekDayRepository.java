package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.TrainerScheduleWeekDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrainerScheduleWeekDayRepository extends JpaRepository<TrainerScheduleWeekDays,Long> {
}
