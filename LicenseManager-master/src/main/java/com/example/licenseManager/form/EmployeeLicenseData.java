package com.example.licenseManager.form;

import java.time.LocalDate;

import com.example.licenseManager.entity.Employee;
import com.example.licenseManager.entity.EmployeeLicense;
import com.example.licenseManager.entity.License;

import lombok.Data;

@Data
public class EmployeeLicenseData {

	// 社員
	private Employee employee;

	// 資格
	private License license;

	// 取得日
	private String getDate;

	// 届出日
	private String notificationDate;
	
	// 付与開始月
	private String startDate;

	// 備考
	private String remarks;

	public EmployeeLicense toEntity() {

		// 所持資格を生成
		EmployeeLicense employeeLicense = new EmployeeLicense();

		// 所持資格情報を保存
		employeeLicense.setEmployee(employee);
		employeeLicense.setLicense(license);
		try {
			employeeLicense.setGetDate(LocalDate.parse(getDate));
		} catch (Exception e) {
			employeeLicense.setGetDate(null);
		}
		try {
			employeeLicense.setNotificationDate(LocalDate.parse(notificationDate));
		} catch (Exception e) {
			employeeLicense.setNotificationDate(null);
		}
		employeeLicense.setStartDate(startDate);
		employeeLicense.setRemarks(remarks);

		return employeeLicense;
	}
}
