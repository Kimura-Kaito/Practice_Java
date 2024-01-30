package com.example.licenseManager.form;

import java.sql.Date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LicenseManagerData {
	//社員番号
	private Integer id;
	//氏名
	@NotNull(message = "氏名が入力されていません")
	private String name;
	//試験名
	private String exam;
	//取得日
	private Date get_date;
	//届出日
	private Date notification_date;
	//手当金額
	private String salary;
	//給与開始日
	private Date salary_start_date;
	//備考
	private String remarks;
	
}