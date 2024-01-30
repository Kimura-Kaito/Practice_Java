package com.example.licenseManager.common;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//文字列をDate型へ変換するクラス
public class StrDate {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date strdate(String s) {
		long ms = 0;
		try {
			ms = sdf.parse(s).getTime();
		}catch(ParseException e) {
			e.printStackTrace();
		}
		return new Date(ms);
	}
}
