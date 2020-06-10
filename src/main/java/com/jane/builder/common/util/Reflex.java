package com.jane.builder.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.jdbc.core.RowMapper;

public class Reflex {

	public static final String UNDERLINE_PATTERN = "_.";
	
	public static final int N32 = 32;
    public static final int N97 = 97;
    public static final int N122 = 122;
    public static final int N65 = 65;
    public static final int N90 = 90;
    
    public static final String UNDERLINE = "_";
    
    public static final String SET = "set";
    public static final String GET = "get";
    
    private static final Object[] BLANK_ARRAY = new Object[0];

    public static String underlineToHump(String str){
        Pattern p = Pattern.compile(UNDERLINE_PATTERN);
        Matcher m = p.matcher(str);
        while(m.find()){
            String s = m.group();
            char c = s.charAt(1);
            if(c>=N97&&c<=N122){
                c = (char) (c-N32);
            }
            str = str.replace(s, String.valueOf(c));
        }
        return str;
    }

    public static String humpToUnderline(String str){
        StringBuffer bf = new StringBuffer();
        for(int i=0, len=str.length(); i<len; i++){
            char c = str.charAt(i);
            if(c>=N65&&c<=N90){
                c = (char)(c+N32);
                bf.append(UNDERLINE);
            }
            bf.append(c);
        }
        return bf.toString();
    }
    
    public static String upperFirst(String str) {
    	if(X.isBlank(str)) {
    		return str;
    	}
    	char oc = str.charAt(0);
    	char nc = oc;
    	if(oc>=N97&&oc<=N122) {
    		nc = (char)(oc-N32);
    	}
    	return nc+str.substring(1);
    }

    public static String lowerFirst(String str){
		if(X.isBlank(str)) {
			return str;
		}
		char oc = str.charAt(0);
		char nc = oc;
		if(oc>=N65&&oc<=N90) {
			nc = (char)(oc+N32);
		}
		return nc+str.substring(1);
	}
    
	public static Method writeMethod(Class<?> clazz, Field f) {
		try {
			return clazz.getMethod(SET+upperFirst(f.getName()), f.getType());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object read(Object o, Field field){
		Method m = readMethod(o.getClass(), field);
		try {
			Object value = m.invoke(o, BLANK_ARRAY);
			Class<?> clazz = field.getType();
			if(X.isBoolean(clazz)){
				Boolean b = (Boolean) value;
				value = booleanToInt(b);
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
    public static Method readMethod(Class<?> clazz, Field f){
		try {
			return clazz.getMethod(GET+upperFirst(f.getName()), new Class<?>[] {});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
	}

    public static <T> RowMapper<T> create(Class<T> clazz){
        return new RowMapper<T>() {
			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				T t = null;
				try {
					t = clazz.newInstance();
					for (int i = 1; i <= columnCount; i++) {
						String columnName = metaData.getColumnName(i);
						String hump = underlineToHump(columnName);
						getGeneralField(clazz);
						Field f = getFieldByName(clazz, hump);
						Method m = writeMethod(clazz, f);
						m.invoke(t, convertObject(rs.getObject(i), f.getType()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return t;
			}
        };
    }
    
    public static Field getFieldByName(Class<?> clazz, String name) throws SecurityException{
    	try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			return getFieldByName(clazz.getSuperclass(), name);
		}
    }
    
    public static Object convertObject(Object o, Class<?> clazz) {
    	if(X.isBoolean(clazz)) {
    		return ObjectToBoolean(o);
    	}else if(clazz.equals(Date.class)){
    		return ObjectToDate(o);
    	}else {
    		return o;
    	}
    }

    public static Integer booleanToInt(Boolean b){
    	Integer res;
    	if(b==null){
    		res = 0;
		}else{
    		res = b?1:0;
		}
    	return res;
	}

    public static Boolean ObjectToBoolean(Object o) {
    	if(o!=null){
			if(o instanceof Boolean) {
				return (Boolean) o;
			}else if(o instanceof Integer) {
				Integer value = (Integer) o;
				return value>0;
			}else {
				return false;
			}
		}else{
    		return null;
		}
    }

    public static Long dateToLong(Date d){
    	if(d!=null){
    		return d.getTime();
		}else{
			return null;
		}
	}
    
    public static Date ObjectToDate(Object o) {
    	if(o!=null) {
    		Long value;
    		if(o instanceof Date) {
    			return (Date) o;
    		}else if(o instanceof Long) {
    			value = (Long) o;
    		}else if(o instanceof Integer) {
    			Integer iv = (Integer) o;
    			value = 1000L*iv;
    		}else {
    			value = Long.parseLong(String.valueOf(o));
    		}
    		return new Date(value);
    	}else {
    		return null;
    	}
	}

    public static String[] getPKNames(Class<?> clazz) {
		List<Field> pkFields = getPKFields(clazz);
		int len = pkFields.size();
		String[] pknames = new String[len];
		for(int i=0; i<len; i++) {
			pknames[i] = pkFields.get(i).getName();
		}
		return pknames;
	}
	
	public static Field getPKField(Class<?> clazz) {
		List<Field> pks = getPKFields(clazz);
		return pks.get(0);
	}
	
	public static List<Field> getGeneralField(Class<?> clazz) {
		List<Field> list = new ArrayList<>();
		return getGeneralField(clazz, list);
    }
	
	public static List<Field> getGeneralField(Class<?> clazz, List<Field> fields){
		if(clazz==Object.class) {
			return fields;
		}else {
			Field[] currentFields = clazz.getDeclaredFields();
			for(int i=0; i<currentFields.length; i++) {
	    		Field f = currentFields[i];
	    		if(!Modifier.isStatic(f.getModifiers())) {
	    			fields.add(f);
	    		}
	    	}
			return getGeneralField(clazz.getSuperclass(), fields);
		}
	}
	
	public static List<Field> getPKFields(Class<?> clazz){
		List<Field> res = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		for(int i=0; i<fields.length; i++){
			Field f = fields[i];
			if(f.isAnnotationPresent(Id.class)){
				res.add(f);
			}
		}
		return res;
	}
	
	public static String getTable(Class<?> clazz){
		String table = null;
		if(clazz.isAnnotationPresent(Table.class)) {
			Table t = clazz.getAnnotation(Table.class);
			table = t.name();
		}
		if(X.isBlank(table)) {
			table = humpToUnderline(lowerFirst(clazz.getSimpleName()));
		}
		return table;
	}
	
	public static String getKey(Field field){
		String key = null;
		if(field.isAnnotationPresent(Column.class)){
			Column column = field.getAnnotation(Column.class);
			String name = column.name();
			if(!X.isBlank(name)){
				key = name;
			}
		}
		if(X.isBlank(key)){
			key = humpToUnderline(field.getName());
		}
		return key;
	}

}
