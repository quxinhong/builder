package com.jane.builder.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.DictionaryModel;
import com.jane.builder.modules.model.DictionaryPK;

public interface DictionaryRepository extends JpaRepository<DictionaryModel, DictionaryPK>{

	void deleteByModuleNo(String moduleNo);
}
