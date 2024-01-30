package com.example.licenseManager.exception;

public class MyException extends Exception {

	// Warning回避
	private static final long serialVersionUID = 1L;

	// コンストラクタ
	public MyException(String msg) {
		super(msg);
	}
}
