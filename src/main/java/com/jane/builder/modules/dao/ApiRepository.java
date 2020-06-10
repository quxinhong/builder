package com.jane.builder.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.ApiModel;
import com.jane.builder.modules.model.ApiPK;

public interface ApiRepository extends JpaRepository<ApiModel, ApiPK>{

	void deleteByModuleNo(String moduleNo);
}
