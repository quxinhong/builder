package com.jane.builder.common.constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jane.builder.modules.model.CommonItemModel;
import com.jane.builder.modules.model.CommonModel;

public class ApiType {

	public static final String STD = "STD";
	public static final String OBJ = "OBJ";
	public static final String BIZ = "BIZ";
	
	public static final String comNo = "apiType";
	
	public static List<CommonItemModel> items = new ArrayList<>();
	
	public static CommonModel COMMON = new CommonModel();
	
	static {
		COMMON.setComNo(comNo);
		COMMON.setComName("接口类型");
		COMMON.setEditable(false);
		COMMON.setCreateDate(new Date());
		COMMON.setCreateUser(C.SYSTEM);
		items.add(new CommonItemModel(comNo, STD, "标准查询", 1, null, null, null));
		items.add(new CommonItemModel(comNo, OBJ, "对象查询", 2, null, null, null));
		items.add(new CommonItemModel(comNo, BIZ, "业务逻辑", 3, null, null, null));
	}
}
