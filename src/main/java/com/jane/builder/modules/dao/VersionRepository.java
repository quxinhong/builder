package com.jane.builder.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.VersionModel;

public interface VersionRepository extends JpaRepository<VersionModel, Integer>{

}
