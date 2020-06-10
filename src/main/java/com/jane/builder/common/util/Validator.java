package com.jane.builder.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jane.builder.common.standard.R;
import com.jane.builder.modules.model.DictionaryModel;

public class Validator {

	public static R validate(Object value, DictionaryModel dic) {
		if(!dic.getNullable()) {
			if(X.isBlank(value)) {
				return R.err(dic.getFieldLabel()+"不能为空");
			}
		}
		String type = dic.getFieldType();
		if(value!=null) {
			String str = value.toString();
			if(dic.getRegExp()!=null) {
				if(!str.matches(dic.getRegExp())) {
					return R.err(dic.getFieldLabel()+"格式错误");
				}
			}
			if(type.startsWith("string")) {
				
				if(type.matches("^string\\(\\d+\\)$")) {
					Pattern p = Pattern.compile("\\d+");
					Matcher m = p.matcher(type);
					m.find();
					String n = m.group(0);
					Integer len = Integer.valueOf(n);
					if(len<str.length()) {
						return R.err(dic.getFieldLabel()+"长度超出限制");
					}
				}
			}
			if(type.startsWith("number")) {
				String reg = "^";
				if(str.startsWith("-")) {
					reg = reg+"-";
				}
				if(type.matches("^number\\(\\d+[\\S]*\\)$")) {
					Pattern p = Pattern.compile("\\d+");
					Matcher m = p.matcher(type);
					List<String> list = new ArrayList<>();
					while(m.find()) {
						list.add(m.group());
					}
					if(list.size()==1) {
						reg = reg+"\\d{1,"+list.get(0)+"}$";
					}else if(list.size()==2) {
						reg = reg+"\\d{1,"+list.get(0)+"}(\\.\\d{1,"+list.get(1)+"})?$";
					}else {
						reg = reg+"\\d+(\\.\\d+)?$";
					}
				}else {
					reg = reg+"\\d+(\\.\\d+)?$";
				}
				if(!str.matches(reg)) {
					return R.err(dic.getFieldLabel()+"格式错误");
				}
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static R validateBatch(Map<String, Object> map, Map<String, DictionaryModel> dicMap) {
		for (String key : map.keySet()) {
			Object o = map.get(key);
			if(o instanceof Map) {
				Map<String, Object> cMap = (Map<String, Object>) o;
				R r = validateBatch(cMap, dicMap);
				if(r!=null) {
					return r;
				}
			}else if(o instanceof List) {
				List<Map<String, Object>> list = (List<Map<String, Object>>) o;
				for (Map<String, Object> cMap : list) {
					R r = validateBatch(cMap, dicMap);
					if(r!=null) {
						return r;
					}
				}
			}else {
				R r = validate(o, dicMap.get(key));
				if(r!=null) {
					return r;
				}
			}
		}
		return null;
	}
}
