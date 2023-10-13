package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Employees;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

    Employees findEmployeesByEmail(Account email);
    List<Employees> findEmployeesByZooArea(ZooArea zooArea);

    List<Employees> findEmployeesByActiveIsTrue();
}
