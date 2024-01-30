package com.example.licenseManager.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.licenseManager.entity.EmployeeLicense;

@Transactional
public interface EmployeeLicenseRepository extends JpaRepository<EmployeeLicense,Integer>{
	//追加(20231113)
/*
	@Procedure(name = "Regist_func", outputParameterName = "rec_dummy")
	Integer regist(
			@Param("employee") Integer employee, 
			@Param("licese") Integer license,
			@Param("getdate") Date getdate,
			@Param("notificationdate") Date notificationdate,
			@Param("startdate") String startdate,
			@Param("formremarks") String formremarks);
*/

	@Modifying
	@Transactional
	@Query(value = "insert into el_tmp(employee_id, license_id, get_date, notification_date, start_date, remarks) values (:employee, :license, :getdate, :notificationdate, :startdate, :formremarks)", nativeQuery =  true)
	//@Procedure(name = "Insert_tmp", outputParameterName = "rec_dummy")
	//Integer insertTmp(
	void insertTmp(
			@Param("employee") Integer employee,
			@Param("license") Integer license,
			@Param("getdate") Date getdate,
			@Param("notificationdate") Date notificationdate,
			@Param("startdate") String startdate,
			@Param("formremarks") String formremarks);
	
	@Modifying
	@Transactional
	@Query(value = "update employee_license "
			+ "set (get_date, "
			+ "notification_date, "
			+ "start_date, "
			+ "remarks) = "
			+ "(el_tmp.get_date, "
			+ "el_tmp.notification_date, "
			+ "el_tmp.start_date,"
			+ "el_tmp.remarks) "
			+ "from el_tmp "
			+ "where employee_license.employee_id = el_tmp.employee_id "
			+ "and employee_license.license_id = el_tmp.license_id "
			, nativeQuery = true)
	void update_employee_license_exists();

	
	@Modifying
	@Transactional
	@Query(value = "insert into employee_license( "
			+ "employee_id, "
			+ "license_id, "
			+ "get_date, "
			+ "notification_date, "
			+ "start_date, "
			+ "remarks) "
			+ "select el_tmp.employee_id, " 
			+ "el_tmp.license_id, "
			+ "el_tmp.get_date, "
			+ "el_tmp.notification_date, "
			+ "el_tmp.start_date, "
			+ "el_tmp.remarks "
			+ "from (select employee_id, license_id from el_tmp "
			+ "except "
			+ "(select employee_id, license_id from employee_license))as el_except, el_tmp "
			+ "where el_tmp.employee_id = el_except.employee_id and el_tmp.license_id = el_except.license_id"
			, nativeQuery = true)
	void insert_employee_license_not_exists();
	
	//@Procedure(name = "Regist_func_all", outputParameterName = "rec_dummy")
	//Integer registAll(@Param("rec_dummy") Integer rec_dummy);
	
	//el_tmpテーブル作成(20231122)
	@Modifying
	@Query(value = "create table if not exists el_tmp("
			+ "id SERIAL PRIMARY KEY,"
			+ "employee_id INTEGER NOT NULL,"
			+ "license_id INTEGER NOT NULL,"
			+ "get_date DATE,"
			+ "notification_date DATE,"
			+ "start_date TEXT,"
			+ "remarks TEXT,"
			+ "UNIQUE(employee_id, license_id))"
			, nativeQuery = true)
	void table(@Param("table") Integer table);
	
	//el_tmpテーブルデータ削除(20231128)
	@Modifying
	@Query(value = "truncate table el_tmp restart identity"
	, nativeQuery = true)
	void truncate(@Param("truncate") Integer truncate);
	
	//employee_id順にソート(20231127)
	@Query(value = "select * from employee_license order by employee_id", nativeQuery = true)
	List<EmployeeLicense> queryAll();
	
	List<EmployeeLicense> findAllByOrderByEmployeeIdAsc();
}