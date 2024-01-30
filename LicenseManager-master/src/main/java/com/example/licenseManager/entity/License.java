package com.example.licenseManager.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "license")
@ToString(exclude = "level")
public class License {

	// 資格番号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	// 資格名
	@Column(name = "name")
	private String name;

	// レベル
	@ManyToOne
	@JoinColumn(name = "level")
	private Level level;

	// 上位資格番号
	@Column(name = "higher_license_id")
	private Integer[] higherLicenseId;

	// 所持社員リスト
	@OneToMany(mappedBy = "license", cascade = CascadeType.ALL)
	@OrderBy("id asc")
	private List<EmployeeLicense> employeeLicenseList;
}
