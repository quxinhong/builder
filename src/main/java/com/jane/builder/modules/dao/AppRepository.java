package com.jane.builder.modules.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.AppModel;

public interface AppRepository extends JpaRepository<AppModel, String> {

	List<AppModel> findByAppNoLikeOrAppNameLikeOrAppShortNameLike(String arg1, String arg2, String arg3);
}
