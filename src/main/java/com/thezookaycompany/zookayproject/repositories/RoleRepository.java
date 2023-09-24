package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("SELECT r FROM Role r WHERE r.RoleID = :roleId")
    Role findRoleByRoleID(@Param("roleId") String roleId);
    @Query("SELECT r FROM Role r WHERE r.RoleName = :roleName")
    Role findByRoleName(@Param("roleName") String roleName);
}
