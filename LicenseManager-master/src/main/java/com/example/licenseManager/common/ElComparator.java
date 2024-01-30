package com.example.licenseManager.common;

import java.util.Comparator;

import com.example.licenseManager.entity.EmployeeLicense;

//EmployeeLicenseのリストを社員番号、取得日順に並び変えるクラス
public class ElComparator implements Comparator<EmployeeLicense>{
	public int compare(EmployeeLicense el1, EmployeeLicense el2) {
		if(el1.getEmployee().getId() < el2.getEmployee().getId()) {
			return -1;
		}else if(el1.getEmployee().getId() > el2.getEmployee().getId()) {
			return 1;
		}else {
			return el1.getGetDate().compareTo(el2.getGetDate());
		}
	}
}
