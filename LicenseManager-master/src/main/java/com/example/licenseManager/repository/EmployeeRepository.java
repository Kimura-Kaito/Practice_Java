package com.example.licenseManager.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.licenseManager.entity.Employee;

//20231202funayama追加
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,Integer>{
	//追加(20231117)
	//変更funayama20231202
	//@Procedure(procedureName = "func_tmp", outputParameterName = "rec_dummy")
	//@Procedure(procedureName = "func_tmp")
	@Modifying
	@Transactional
	@Query(value = "insert into e_tmp(id, name, department, change_status_date) values (:id, :name, :department, :change_status_date)", nativeQuery =  true)
	void createtmp(@Param("id") Integer id, @Param("name") String name, @Param("department") String department, @Param("change_status_date") Date change_status_date);
	//Integer createtmp(@Param("e_id") Integer e_id, @Param("e_name") String e_name, @Param("e_department") String e_department, @Param("e_changeStatusDate") Date e_changeStatusDate);
	//void createtmp(@Param("e_id") Integer e_id, @Param("e_name") String e_name, @Param("e_department") String e_department, @Param("e_changeStatusDate") Date e_changeStatusDate);

	@Modifying
	@Transactional
	@Query(value = "update employee_license "
			+ "set employee_id = tmp_id.new_id "
			+ "from "
			+ "(select e_old.id old_id, e_new.id new_id "
			+ "from "
			+ "(select * from employee except (select * from e_tmp)) as e_old, "
			+ "(select * from e_tmp except (select * from employee)) as e_new "
			+ "where e_old.name = e_new.name and e_old.id >= 1000 and e_new.id < 1000)as tmp_id "
			+ "where employee_id = tmp_id.old_id "
			, nativeQuery = true)
	void update_employee_license_4_to_3();

	@Modifying
	@Transactional
	@Query(value = "update employee "
			+ "set (id, department, change_status_date) = "
			+ "(e_tmp.id, e_tmp.department, e_tmp.change_status_date) "
			+ "from e_tmp "
			+ "where employee.name = e_tmp.name "
			, nativeQuery = true)
	void update_employee();

	@Modifying
	@Transactional
	@Query(value = "insert into employee "
			+ "select e_tmp.* "
			+ "from (select id from e_tmp "
			+ "except "
			+ "(select id from employee)) as e_except, e_tmp "
			+ "where e_tmp.id = e_except.id "
			, nativeQuery = true)
	void insert_employee();

	@Modifying
	@Transactional
	@Query(value = "delete "
			+ "from employee "
			+ "where "
			+ "id in( "
			+ "select employee.id "
			+ "from (select id from employee "
			+ "except "
			+ "(select id from e_tmp)) as e_except2, employee "
			+ "where employee.id = e_except2.id) "
			, nativeQuery = true)
	void delete_employee_no_emp();

	@Modifying
	@Transactional
	@Query(value = "delete " 
			+ "from employee_license "
			+ "where employee_id not in (select id from employee) "
			, nativeQuery = true)
	void delete_employee_license_no_emp();
	
	//el_tmpテーブルデータ削除(20231128)
	@Modifying
	@Query(value = "truncate table e_tmp restart identity"
	, nativeQuery = true)
	void truncate_e_tmp();

	
	////@Procedure(procedureName = "func_update_employee", outputParameterName = "update_out")
	//@Procedure(procedureName = "func_update_employee")
	//void updateemployee(@Param("update_out") Integer update_out);

	
	
	
	/*
	@Procedure(name = "Create_tmp", outputParameterName = "rec_dummy")
	Integer createtmp(
			@Param("e_id") Integer id,
			@Param("e_name") String name,
			@Param("e_department") String department,
			@Param("e_changeStatusDate") Date changeStatusDate);

	@Procedure(name = "Update_employee", outputParameterName = "update_out")
	Integer updateemployee(
			@Param("update_out") Integer update_out);
*/	
	
	//id順にソート
	@Query(value = "select * from employee order by id", nativeQuery = true)
	List<Employee> queryAll();

	
	List<Employee> findByNameLike(String name);
	
}