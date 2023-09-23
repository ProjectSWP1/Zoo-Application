package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.*;

@Entity
public class Role {

    @Id
    @Column(name = "RoleID", nullable = false, length = 2)
    private String RoleID;

    @Column(name = "roleName", nullable = false)
    private String RoleName;

    @OneToOne(mappedBy = "role")
    private Account account;

}
