package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.EmployeesDto;
import com.thezookaycompany.zookayproject.model.entity.Employees;

import java.util.List;

public interface EmployeeService {
    // Lấy toàn bộ Employee (kể cả đã nghỉ việc)
    List<Employees> getAllEmployees();

    // Lấy Employee đang quản lý Zoo Area ID
    List<Employees> getEmployeesManageZooArea(String zooAreaID);

    // Lấy list Employee đang active (chưa nghỉ việc)
    List<Employees> getActiveEmployees();

    // Thêm employees
    String addEmployees(EmployeesDto employeesDto);

    // Sa thải employees (disable employee)
    String deactivateEmployees(Integer empID);

    // Update employees
    String updateEmployees(EmployeesDto employeesDto);
}
