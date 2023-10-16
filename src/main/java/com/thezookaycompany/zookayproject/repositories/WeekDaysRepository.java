package com.thezookaycompany.zookayproject.repositories;


import com.thezookaycompany.zookayproject.model.entity.WeekDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface WeekDaysRepository extends JpaRepository<WeekDays, Integer> {
}
