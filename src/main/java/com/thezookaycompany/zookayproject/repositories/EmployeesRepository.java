package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesRepository extends JpaRepository<Employees, Integer> {
}
