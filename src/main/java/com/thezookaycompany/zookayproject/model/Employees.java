package com.thezookaycompany.zookayproject.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "Employees")
public class Employees {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer empId;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 12)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate doB;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zoo_area_id", nullable = false)
    private ZooArea zooArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managed_by_emp_id", nullable = false)
    private Employees managedByEmp;

    @OneToMany(mappedBy = "managedByEmp")
    private Set<Employees> managedByEmpEmployeess;

    @OneToMany(mappedBy = "emp")
    private Set<TrainerSchedule> empTrainerSchedules;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(final Integer empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDoB() {
        return doB;
    }

    public void setDoB(final LocalDate doB) {
        this.doB = doB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public ZooArea getZooArea() {
        return zooArea;
    }

    public void setZooArea(final ZooArea zooArea) {
        this.zooArea = zooArea;
    }

    public Employees getManagedByEmp() {
        return managedByEmp;
    }

    public void setManagedByEmp(final Employees managedByEmp) {
        this.managedByEmp = managedByEmp;
    }

    public Set<Employees> getManagedByEmpEmployeess() {
        return managedByEmpEmployeess;
    }

    public void setManagedByEmpEmployeess(final Set<Employees> managedByEmpEmployeess) {
        this.managedByEmpEmployeess = managedByEmpEmployeess;
    }

    public Set<TrainerSchedule> getEmpTrainerSchedules() {
        return empTrainerSchedules;
    }

    public void setEmpTrainerSchedules(final Set<TrainerSchedule> empTrainerSchedules) {
        this.empTrainerSchedules = empTrainerSchedules;
    }

}
