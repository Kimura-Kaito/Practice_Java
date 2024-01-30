package com.example.licenseManager.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "level")
public class Level {

	// レベル番号
	@Id
	@Column(name = "id")
	private Integer id;

	// 手当金額
	@Column(name = "allowance")
	private Integer allowance;

	// 手当上限額
	@Column(name = "max")
	private Integer max;

	// レベル別資格リスト
	@OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
	@OrderBy("id asc")
	private List<License> licenseList;
}
