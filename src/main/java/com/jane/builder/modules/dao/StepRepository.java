package com.jane.builder.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jane.builder.modules.model.StepModel;
import com.jane.builder.modules.model.StepPK;

public interface StepRepository extends JpaRepository<StepModel, StepPK>{
	
	void deleteByModuleNo(String moduleNo);
	
	void deleteByModuleNoAndApiNo(String moduleNo, String apiNo);
	
	@Query("SELECT MAX(idx) FROM StepModel WHERE moduleNo = ?1 AND apiNo = ?2")
    Integer findMaxIdx(String moduleNo, String apiNo);
}
