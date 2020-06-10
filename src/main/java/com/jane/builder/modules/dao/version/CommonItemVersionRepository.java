package com.jane.builder.modules.dao.version;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.version.CommonItemVersionModel;
import com.jane.builder.modules.model.version.CommonItemVersionPK;

public interface CommonItemVersionRepository extends JpaRepository<CommonItemVersionModel, CommonItemVersionPK>{

}
