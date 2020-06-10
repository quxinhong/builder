package com.jane.builder.modules.service.core.spec;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.fastjson.JSON;
import com.jane.builder.common.constant.DBType;
import com.jane.builder.common.constant.OrderRule;
import com.jane.builder.common.constant.QueryCondition;
import com.jane.builder.common.constant.TotalMethod;
import com.jane.builder.common.util.SqlX;
import com.jane.builder.modules.model.DictionaryModel;
import com.jane.builder.modules.service.core.StdQuery;

public class MysqlStdQuery extends StdQuery{

	

	@Override
	public Map<String, Object> query(String sql, Boolean sum, Integer currentPage, Integer pageSize,
									Map<String, List<Map<String, Object>>> queryParams, List<DictionaryModel> dictionarys) throws SQLException {
		List<Object> params = new ArrayList<>();
		Map<String, Object> res = new HashMap<>();
		List<SQLStatement> ss = SQLUtils.parseStatements(sql, DBType.MYSQL);
		sql = ss.get(0).toString().trim();
		if(sql.endsWith(";")) {
			sql = sql.substring(0, sql.length()-1);
		}
		sql = initFilterSql(sql, queryParams, params, dictionarys);
		System.out.println(sql);
		if(sum&&canSum(dictionarys)){
			String sumSql = initSumSql(sql, dictionarys);
			System.out.println(sumSql);
			System.out.println(JSON.toJSONString(params));
			List<Map<String, Object>> list = sqlExecutor.excuteStdQuery(sumSql, params);
			res.put("sumRow", list);
		}
		if(currentPage!=null&&pageSize!=null){
			String countSql = initCountSql(sql);
			System.out.println(countSql);
			List<Map<String, Object>> list = sqlExecutor.excuteStdQuery(countSql, params);
			res.put("total", list);
		}
		sql = initOrderSql(sql, queryParams);
		System.out.println(sql);
		if(currentPage!=null&&pageSize!=null){
			sql = initPageSql(sql, currentPage, pageSize, params);
			System.out.println(sql);
		}
		List<Map<String, Object>> list = sqlExecutor.excuteStdQuery(sql, params);
		res.put("list0", list);
		return res;
	}

	public String initPageSql(String sql, Integer currentPage, Integer pageSize, List<Object> params){
		Integer index = (currentPage-1)*pageSize;
		sql = sql + " LIMIT ?, ?";
		params.add(index);
		params.add(pageSize);
		return sql;
	}

	public String initOrderSql(String sql,
						Map<String, List<Map<String, Object>>> queryParams){
		List<Map<String, Object>> orders = queryParams.get("orders");
		if(orders!=null&&orders.size()>0){
			sql = sql+" ORDER BY ";
			for(int i=0; i<orders.size(); i++){
				Map<String, Object> order = orders.get(i);
				String role = (String) order.get("rule");
				sql = sql+String.format(OrderRule.ruleSqlMapping.get(role), order.get("field"))+", ";
			}
			sql = sql.substring(0, sql.length()-2);
		}
		return sql;
	}

	public String initCountSql(String sql){
		return "SELECT COUNT(*) count FROM ("+sql+") countTable";
	}

	public String initSumSql(String sql, List<DictionaryModel> dictionarys){
		String pre = "SELECT ";
		for (int i=0; i<dictionarys.size(); i++){
			DictionaryModel dictionaryModel = dictionarys.get(i);
			String totalMethod = dictionaryModel.getTotalMethod();
			if(totalMethod!=null){
				pre = pre+String.format(TotalMethod.methodSqlMapping.get(totalMethod), dictionaryModel.getFieldName(), dictionaryModel.getFieldName())+", ";
			}
		}
		pre = pre.substring(0, pre.length()-2);
		return pre = pre+" FROM ("+sql+") sumTable";
	}

	public String initFilterSql(String sql,
						  Map<String, List<Map<String, Object>>> queryParams,
						  List<Object> params,
						  List<DictionaryModel> dictionarys){
		List<String> fields = new ArrayList<>();
		sql = SqlX.replacePlaceholders(sql, fields);
		sql = "SELECT * FROM ("+sql+") base WHERE 1 = 1 ";
		List<Map<String, Object>> step0 = queryParams.get("step0");
		if(step0!=null&&step0.size()>0) {
			Map<String, Object> baseParam = step0.get(0);
			if(baseParam!=null) {
				for(int i=0; i<fields.size(); i++) {
					params.add(baseParam.get(fields.get(i)));
				}
			}
		}
		List<Map<String, Object>> filters = queryParams.get("filters");
		Map<String, DictionaryModel> dicMap = toMap(dictionarys);
		if(filters!=null&&filters.size()>0) {
			for(int i=0; i<filters.size(); i++) {
				sql = sql+" AND ";
				Map<String, Object> filter = filters.get(i);
				sql = sql + getFilterItem(filter, params, dicMap);
			}
		}
		return sql;
	}

	private String getFilterItem(Map<String, Object> filter, List<Object> params, Map<String, DictionaryModel> dicMap) {
		String field = (String) filter.get("field");
		String condition = (String) filter.get("condition");
		Object value = filter.get("value");
		DictionaryModel dictionary = dicMap.get(field);
		String res = "";
		if(isDate(dictionary.getFieldType())&&QueryCondition.EQ.equals(condition)){
			String strDate = value+"";
			res = res+String.format(QueryCondition.conditionSqlMapping.get(QueryCondition.GE), field);
			params.add(strDate+QueryCondition.MIN_DATE.substring(strDate.length()));
			res = res+String.format(QueryCondition.conditionSqlMapping.get(QueryCondition.LE), field);
			params.add(strDate+QueryCondition.MAX_DATE.substring(strDate.length()));
		}else{
			res = res + String.format(QueryCondition.conditionSqlMapping.get(condition), field);
			if(!QueryCondition.IS_NULL.equals(condition)&&!QueryCondition.NOT_NULL.equals(condition)){
				params.add(value);
			}
		}
		return res;
	}

	private boolean canSum(List<DictionaryModel> dictionarys) {
		for(int i=0; i<dictionarys.size(); i++) {
			if(dictionarys.get(i).getTotalMethod()!=null) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isDate(String fieldType){
		return fieldType.toLowerCase().indexOf("date")!=-1;
	}

	private Map<String, DictionaryModel> toMap(List<DictionaryModel> dictionarys){
		Map<String, DictionaryModel> res = new HashMap<>();
		for (int i=0; i<dictionarys.size(); i++){
			DictionaryModel dictionaryModel = dictionarys.get(i);
			res.put(dictionaryModel.getFieldName(), dictionaryModel);
		}
		return res;
	}
}
