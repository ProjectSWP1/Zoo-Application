package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "RoleID", nullable = false, length = 2)
    private String RoleID;

    public Role(String roleName) {
        RoleName = roleName;
    }

    @Column(name = "RoleName", nullable = false)
    private String RoleName;

    @OneToOne(mappedBy = "role")
    private Account account;

    public Role(String roleID, String roleName) {
        RoleID = roleID;
        RoleName = roleName;
    }

    public Role() {

    }

    @Override
    public String getAuthority() {
        return this.RoleName;
    }


}
