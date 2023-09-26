package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "Employees")
public class Employees {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 12)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate doB;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", nullable = false)
    private Account email;

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZooAreaID", nullable = false)
    private ZooArea zooArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managed_by_empid", nullable = false)
    private Employees managedByEmp;

    @OneToMany(mappedBy = "managedByEmp")
    private Set<Employees> managedByEmpEmployees;

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

    public Account getEmail() {
        return email;
    }

    public void setEmail(final Account email) {
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

    public Set<Employees> getManagedByEmpEmployees() {
        return managedByEmpEmployees;
    }

    public void setManagedByEmpEmployees(final Set<Employees> managedByEmpEmployees) {
        this.managedByEmpEmployees = managedByEmpEmployees;
    }

    public Set<TrainerSchedule> getEmpTrainerSchedules() {
        return empTrainerSchedules;
    }

    public void setEmpTrainerSchedules(final Set<TrainerSchedule> empTrainerSchedules) {
        this.empTrainerSchedules = empTrainerSchedules;
    }

}