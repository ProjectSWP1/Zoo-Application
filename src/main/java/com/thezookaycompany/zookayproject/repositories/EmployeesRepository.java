package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeesRepository extends JpaRepository<Employees, Long> {
}
