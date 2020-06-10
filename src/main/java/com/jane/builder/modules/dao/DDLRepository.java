package com.jane.builder.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jane.builder.modules.model.DDLModel;

import java.util.List;

public interface DDLRepository extends JpaRepository<DDLModel, Integer> {

    @Query("SELECT new DDLModel(ddlId, ddlName, remark) FROM DDLModel")
    List<DDLModel> findDDLIdxs();
    
    @Query("SELECT MAX(ddlId) FROM DDLModel")
    Integer findLastId();
}
