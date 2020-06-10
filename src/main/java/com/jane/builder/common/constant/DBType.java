package com.jane.builder.common.constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.druid.util.JdbcConstants;
import com.jane.builder.modules.model.CommonItemModel;
import com.jane.builder.modules.model.CommonModel;

public class DBType {

	public static final String MYSQL = JdbcConstants.MYSQL;
	
	public static final String comNo = "DBType";
	
	public static List<CommonItemModel> items = new ArrayList<>();
	
	public static CommonModel COMMON = new CommonModel();
	
	static {
		COMMON.setComNo(comNo);
		COMMON.setComName("数据库类型");
		COMMON.setEditable(false);
		COMMON.setCreateDate(new Date());
		COMMON.setCreateUser(C.SYSTEM);
		items.add(new CommonItemModel(comNo, MYSQL, "MYSQL", 1, null, null, null));
	}
}
