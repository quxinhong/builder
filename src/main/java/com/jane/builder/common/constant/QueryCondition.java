package com.jane.builder.common.constant;

import java.util.*;

import com.jane.builder.modules.model.CommonItemModel;
import com.jane.builder.modules.model.CommonModel;

public class QueryCondition {

	public static final String LK = "LK";
	
	public static final String LLK = "LLK";
	
	public static final String RLK = "RLK";
	
	public static final String NLK = "NLK";
	
	public static final String EQ = "EQ";
	
	public static final String NE = "NE";
	
	public static final String GT = "GT";
	
	public static final String LT = "LT";
	
	public static final String GE = "GE";
	
	public static final String LE = "LE";
	
	public static final String IS_NULL = "IS_NULL";
	
	public static final String NOT_NULL = "NOT_NULL";
	
	public static final String comNo = "queryCondition";
	
	public static final List<CommonItemModel> items = new ArrayList<>();
	
	public static final CommonModel COMMON = new CommonModel();

	public static final Map<String, String> conditionSqlMapping = new HashMap<>();

	public static final String MIN_DATE = "0000-00-00 00:00:00.000";

	public static final String MAX_DATE = "9999-12-31 23:59:59.999";
	
	static {
		conditionSqlMapping.put(LK, " %s LIKE ? ");
		conditionSqlMapping.put(LLK, " %s LIKE ? ");
		conditionSqlMapping.put(RLK, " %s LIKE ? ");
		conditionSqlMapping.put(NLK, " %s NOT LIKE ? ");
		conditionSqlMapping.put(EQ, " %s = ? ");
		conditionSqlMapping.put(NE, " %s != ? ");
		conditionSqlMapping.put(GT, " %s > ? ");
		conditionSqlMapping.put(LT, " %s < ? ");
		conditionSqlMapping.put(GE, " %s >= ? ");
		conditionSqlMapping.put(LE, " %s <= ? ");
		conditionSqlMapping.put(IS_NULL, " %s IS NULL ");
		conditionSqlMapping.put(NOT_NULL, " %s IS NOT NULL ");
		COMMON.setComNo(comNo);
		COMMON.setComName("查询条件");
		COMMON.setEditable(false);
		COMMON.setCreateDate(new Date());
		COMMON.setCreateUser(C.SYSTEM);
		items.add(new CommonItemModel(comNo, EQ, "等于", 1, null, null, null));
		items.add(new CommonItemModel(comNo, LK, "包含", 2, null, null, null));
		items.add(new CommonItemModel(comNo, LLK, "左包含", 3, null, null, null));
		items.add(new CommonItemModel(comNo, RLK, "右包含", 4, null, null, null));
		items.add(new CommonItemModel(comNo, NLK, "不包含", 5, null, null, null));
		items.add(new CommonItemModel(comNo, GT, "大于", 6, null, null, null));
		items.add(new CommonItemModel(comNo, LT, "小于", 7, null, null, null));
		items.add(new CommonItemModel(comNo, GE, "大于等于", 8, null, null, null));
		items.add(new CommonItemModel(comNo, LE, "小于等于", 9, null, null, null));
		items.add(new CommonItemModel(comNo, NE, "不等于", 10, null, null, null));
		items.add(new CommonItemModel(comNo, IS_NULL, "为空", 11, null, null, null));
		items.add(new CommonItemModel(comNo, NOT_NULL, "不为空", 12, null, null, null));
	}
}
