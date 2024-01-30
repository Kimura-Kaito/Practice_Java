package com.example.licenseManager.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "employee")
@ToString(exclude = "employeeLicenseList")

public class Employee {
	// 社員番号
	@Id
	@Column(name = "id")
	private Integer id;

	// 氏名
	@Column(name = "name")
	private String name;

	// 所属部門
	@Column(name = "department")
	private String department;

	// 正社員転換日
	@Column(name = "change_status_date")
	private LocalDate changeStatusDate;

	// 資格リスト
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	@OrderBy("id asc")
	private List<EmployeeLicense> employeeLicenseList;
}