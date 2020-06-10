package com.jane.builder.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.RoleModel;

public interface RoleRepository extends JpaRepository<RoleModel, Integer> {

}
