package com.jane.builder.modules.dao.version;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.version.DictionaryVersionModel;
import com.jane.builder.modules.model.version.DictionaryVersionPK;

public interface DictionaryVersionRepository extends JpaRepository<DictionaryVersionModel, DictionaryVersionPK>{

}
