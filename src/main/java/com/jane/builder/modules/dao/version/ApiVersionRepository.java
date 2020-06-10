package com.jane.builder.modules.dao.version;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.version.ApiVersionModel;
import com.jane.builder.modules.model.version.ApiVersionPK;

public interface ApiVersionRepository extends JpaRepository<ApiVersionModel, ApiVersionPK>{

}
