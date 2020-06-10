package com.jane.builder.modules.dao.version;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.version.StepVersionModel;
import com.jane.builder.modules.model.version.StepVersionPK;

public interface StepVersionRepository extends JpaRepository<StepVersionModel, StepVersionPK>{

}
