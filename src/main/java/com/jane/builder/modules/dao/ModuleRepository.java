package com.jane.builder.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.ModuleModel;

public interface ModuleRepository extends JpaRepository<ModuleModel, String>{

}
