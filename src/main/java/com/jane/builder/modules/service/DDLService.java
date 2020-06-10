package com.jane.builder.modules.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jane.builder.modules.dao.DDLRepository;
import com.jane.builder.modules.model.DDLModel;

@Service
public class DDLService {

	@Autowired 
	private DDLRepository ddlRepository;
	
	public DDLModel save(DDLModel ddlModel) {
		return ddlRepository.save(ddlModel);
	}
	
	public List<DDLModel> findDDLIdxs() {
		return ddlRepository.findDDLIdxs();
	}
	
	public Optional<DDLModel> findById(Integer ddlId){
		return ddlRepository.findById(ddlId);
	}
}
