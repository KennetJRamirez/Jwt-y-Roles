package com.umg.control_empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umg.control_empleados.models.RoleEntity;

@Repository
public interface roleRepository extends JpaRepository<RoleEntity, Long> {

}
