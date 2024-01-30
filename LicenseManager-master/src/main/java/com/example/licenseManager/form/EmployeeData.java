package com.example.licenseManager.form;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.licenseManager.entity.Employee;
import com.example.licenseManager.entity.EmployeeLicense;

import lombok.Data;

@Data
public class EmployeeData {
	
	private int id;
	
	private String name;
	
	private String department;
	
	private String changeStatusDate;
	
	public Employee toEntity() {
		
		Employee employee = new Employee();
		
		employee.setId(id);
		employee.setName(name);
		employee.setDepartment(department);
		try {
			employee.setChangeStatusDate(LocalDate.parse(changeStatusDate));
		}catch(Exception e) {
			employee.setChangeStatusDate(null);
		}
		employee.setEmployeeLicenseList(new ArrayList<EmployeeLicense>());
		
		return employee;
	}
}
