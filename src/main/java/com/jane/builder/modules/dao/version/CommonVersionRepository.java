package com.jane.builder.modules.dao.version;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.version.CommonVersionModel;
import com.jane.builder.modules.model.version.CommonVersionPK;

public interface CommonVersionRepository extends JpaRepository<CommonVersionModel, CommonVersionPK>{

}
