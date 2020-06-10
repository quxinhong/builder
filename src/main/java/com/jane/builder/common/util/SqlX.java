package com.jane.builder.common.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jane.builder.modules.model.StepModel;

public class SqlX {

	public static final String PARAM_PATTERN = ":[A-Za-z][A-Za-z0-9_]*";

	public static final String STRING = "string";
	
	public static final String DATE = "date";
	
	public static final String DATE_TIME = "datetime";
	
	public static final String NUMBER = "number";
    
    public static final String getParamName(String matchedStr) {
    	return matchedStr.substring(1);
    }
    
    public static final String filterNotes(String script) {
    	String[] scriptLines = script.split("\n");
		StringBuffer temp = new StringBuffer();
		for(int i=0; i<scriptLines.length; i++) {
			String line = scriptLines[i];
			int idx1 = line.indexOf("#");
			if(idx1!=-1) {
				line = line.substring(idx1+1);
			}
			int ids2 = line.indexOf("-- ");
			if(ids2!=-1) {
				line = line.substring(0, ids2);
			}
			temp.append(line).append("\n");
		}
		return temp.toString();
    }

    public static final Collection<String> searchParams(String script){
    	return searchParamsMap(script).keySet();
    }
    
    public static final Map<String, Object> searchParamsMap(String script){
    	script = filterNotes(script);
    	Map<String, Object> map = new HashMap<>();
    	Pattern pat = Pattern.compile(SqlX.PARAM_PATTERN);
		Matcher m = pat.matcher(script);
		while(m.find()) {
			map.put(getParamName(m.group()), null);
		}
		return map;
    }
    
    public static final Collection<String> searchParams(List<StepModel> querys){
    	Map<String, Object> map = new HashMap<>();
    	for(int i=0; i<querys.size(); i++) {
    		map.putAll(searchParamsMap(querys.get(i).getScript()));
    	}
		return map.keySet();
    }

	public static final String replacePlaceholders(String sql, List<String> fields){
		Pattern pat = Pattern.compile(SqlX.PARAM_PATTERN);
		Matcher m = pat.matcher(sql);
		while(m.find()) {
			String field = m.group();
			sql = sql.replace(field, "?");
			field = getParamName(field);
			fields.add(field);
		}
		return sql;
	}
    
    public static final void close(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
    public static final void close(Statement st) {
		if(st!=null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
    public static final void close(ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
    public static final void rollback(Connection con) {
		if(con!=null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
    public static final void end(Connection con) {
		if(con!=null){
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static final Map<String, Object> defValues = new HashMap<>();
    public static final Date objDate = new Date(0);
    static {
		defValues.put("java.lang.Boolean", false);
		defValues.put("java.lang.Double", 0.0);
		defValues.put("java.lang.Float", 0.0);
		defValues.put("java.lang.Integer", 0);
		defValues.put("java.lang.Long", 0L);
		defValues.put("java.lang.String", "");
		defValues.put("java.math.BigDecimal", 0);
		defValues.put("java.sql.Date", objDate);
		defValues.put("java.sql.Time", objDate);
		defValues.put("java.sql.Timestamp", objDate);
	}

	public static Object getDefaultValue(String className){
		return defValues.get(className);
	}
}
