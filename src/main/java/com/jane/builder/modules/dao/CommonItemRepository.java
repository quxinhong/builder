package com.jane.builder.modules.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.CommonItemModel;
import com.jane.builder.modules.model.CommonItemPK;

public interface CommonItemRepository extends JpaRepository<CommonItemModel, CommonItemPK> {
	List<CommonItemModel> findByComNo(String comNo);
	void deleteByComNo(String comNo);
}
