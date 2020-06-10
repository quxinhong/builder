package com.jane.builder.modules.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.CommonModel;

public interface CommonRepository extends JpaRepository<CommonModel, String> {
	List<CommonModel> findByComNoLikeOrComNameLike(String keyword, String keyword2);
}
