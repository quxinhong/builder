package com.jane.builder.modules.dao.version;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.version.ModuleVersionModel;
import com.jane.builder.modules.model.version.ModuleVersionPK;

public interface ModuleVersionRepository extends JpaRepository<ModuleVersionModel, ModuleVersionPK>{

}
