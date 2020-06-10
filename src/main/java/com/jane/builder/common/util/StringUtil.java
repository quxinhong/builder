package com.jane.builder.common.util;

import java.security.MessageDigest;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.codec.binary.Hex;

public class StringUtil {
	
	public static String encodeHexString(String string) {
		return encodeHexString(string.getBytes());
	}
	
	public static String encodeHexString(byte[] bytes) {
		return Hex.encodeHexString(bytes);
	}
	
	public static String toMD5HexString(String text) {
		try {
			return StringUtil.encodeHexString(MessageDigest.getInstance("MD5").digest(text.getBytes())).toUpperCase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String arrayToString(Object[] array){
		return JSONUtils.toJSONString(array);
	}
}
