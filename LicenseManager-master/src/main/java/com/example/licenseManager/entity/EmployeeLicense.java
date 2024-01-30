package com.example.licenseManager.entity;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "employee_license")
@ToString(exclude = { "employee", "license" })

//追加(20231113)
/*
@NamedStoredProcedureQuery(name = "Regist_func", procedureName = "func_regist", 
parameters = {
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "employee", type = Integer.class),
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "licese", type = Integer.class),
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "getdate", type = Date.class),
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "notificationdate", type = Date.class),
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "startdate", type = String.class),
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "formremarks", type = String.class),
		@StoredProcedureParameter(
				mode = ParameterMode.OUT, name = "rec_dummy", type = Integer.class)
})
*/
//追加(20231114)
/*
@NamedStoredProcedureQuery(name = "Insert_tmp", procedureName = "func_insert_tmp",
parameters = {
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "employee", type = Integer.class),
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "license", type = Integer.class),
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "getdate", type = Date.class),
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "notificationdate", type = Date.class),
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "startdate", type = String.class),
		@StoredProcedureParameter(
				mode = ParameterMode.IN, name = "formremarks", type = String.class),
		@StoredProcedureParameter(
				mode = ParameterMode.OUT, name = "rec_dummy", type = Integer.class)
})
*/
//追加(20231115)
/*
@NamedStoredProcedureQuery(name = "Regist_func_all", procedureName = "func_regist_all",
parameters = {
		@StoredProcedureParameter(
				mode = ParameterMode.INOUT, name = "rec_dummy", type = Integer.class)
})
*/

public class EmployeeLicense {

	// 所持番号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	// 所持社員
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	// 資格資格
	@ManyToOne
	@JoinColumn(name = "license_id")
	private License license;

	// 取得日
	@Column(name = "get_date")
	private LocalDate getDate;

	// 届出日
	@Column(name = "notification_date")
	private LocalDate notificationDate;

	// 付与開始月
	@Column(name = "start_date")
	private String startDate;

	// 備考
	@Column(name = "remarks")
	private String remarks;
}
