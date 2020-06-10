package com.jane.builder.modules.service.core;

import com.jane.builder.modules.SqlExecutor;
import com.jane.builder.modules.model.DictionaryModel;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class StdQuery {

	@Autowired
	protected SqlExecutor sqlExecutor;

	public abstract Map<String, Object> query(String sql, Boolean sum, Integer currentPage, Integer pageSize,
											  Map<String, List<Map<String, Object>>> queryParams, List<DictionaryModel> dictionarys) throws SQLException;
}
