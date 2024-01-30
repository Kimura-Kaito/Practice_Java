package com.example.licenseManager.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.licenseManager.entity.Employee;
import com.example.licenseManager.entity.EmployeeLicense;
import com.example.licenseManager.entity.Level;
import com.example.licenseManager.entity.License;
import com.example.licenseManager.repository.EmployeeLicenseRepository;
import com.example.licenseManager.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LicenseManagerService {
	//フィールド追加(20231127)
	@Autowired
	EmployeeLicenseRepository employeeLicenseRepository;
	EmployeeRepository employeeRepository;
	
	// 非表示資格リストを取り出す
	public List<License> getHiddenLicenseList(List<Level> levelList) {

		// 非表示資格リストを生成
		List<License> hiddenLicenseList = new ArrayList<>();

		// 資格分繰り返し
		for (Level level : levelList) {
			for (License license : level.getLicenseList()) {

				// 所持者が0の場合
				if (license.getEmployeeLicenseList().size() == 0) {

					// 非表示資格を追加
					hiddenLicenseList.add(license);
				}
			}
		}

		return hiddenLicenseList;
	}

	// 資格手当の有無を割り出す
	public Map<Integer, Boolean> isInvalidLicense(List<EmployeeLicense> allEmployeeLicenseList) {

		// 資格手当の有無
		Map<Integer, Boolean> status = new HashMap<Integer, Boolean>();

		// 取得資格分繰り返し
		for (EmployeeLicense employeeLicense : allEmployeeLicenseList) {

			// 社員番号が4桁以上
			if (employeeLicense.getEmployee().getId() >= 1000) {

				// 無効
				status.put(employeeLicense.getId(), true);
				continue;
			}

			// 取得日・正社員転換日が空ではない
			if (employeeLicense.getGetDate() != null && employeeLicense.getEmployee().getChangeStatusDate() != null) {

				// 取得日が正社員転換日よりも前
				if (employeeLicense.getGetDate()
						.isBefore(employeeLicense.getEmployee().getChangeStatusDate())) {

					// 無効
					status.put(employeeLicense.getId(), true);
					continue;
				}
			}

			// 有効
			status.put(employeeLicense.getId(), false);
		}

		return status;
	}

	// 上位資格の有無を割り出す
	public Map<Integer, Boolean> haveHigherLicense(List<EmployeeLicense> allEmployeeLicenseList) {

		// 資格手当の有無
		Map<Integer, Boolean> status = new HashMap<Integer, Boolean>();

		// 取得資格分繰り返し
		for (EmployeeLicense employeeLicense : allEmployeeLicenseList) {

			// 有効（デフォルト）
			status.put(employeeLicense.getId(), false);

			// より上位の資格を取得している
			employeeLicense.getEmployee().getEmployeeLicenseList().forEach(employeeLicenseTemp -> {
				Integer[] higherLicenseId = employeeLicense.getLicense().getHigherLicenseId();
				if (higherLicenseId != null) {
					if (Arrays.asList(higherLicenseId).contains(employeeLicenseTemp.getLicense().getId())) {

						// 無効
						status.put(employeeLicense.getId(), true);
					}
				}
			});
		}

		return status;
	}

	// 資格手当支給額を割り出す
	public Map<Integer, Integer> getEmployeeAllowance(List<Employee> allEmployeeList, List<Level> levelList,
			Map<Integer, Boolean> isInvalidLicense, Map<Integer, Boolean> haveHigherLicense) {

		// レベル別手当支給額合計を生成
		Map<Level, Integer> levelAllowance = new HashMap<Level, Integer>();

		// 各社員手当支給額合計を生成
		Map<Integer, Integer> employeeAllowance = new HashMap<Integer, Integer>();

		// 社員分繰り返し
		for (Employee employee : allEmployeeList) {

			// レベル別手当支給額合計の初期値を保存
			for (Level level : levelList) {
				levelAllowance.put(level, 0);
			}

			// 所持資格分繰り返し
			for (EmployeeLicense employeeLicense : employee.getEmployeeLicenseList()) {

				// 所持資格のレベル
				Level level = employeeLicense.getLicense().getLevel();

				// 条件を満たす
				/*
				 * 所持上位資格の
				 */
				if (!isInvalidLicense.get(employeeLicense.getId()) && !haveHigherLicense.get(employeeLicense.getId())) {

					// レベル別手当支給額合計に加算
					levelAllowance.put(level, levelAllowance.get(level) + level.getAllowance());
				}
			}

			/// レベル別手当支給額合計を加算
			int sum = 0;
			for (Level level : levelList) {
				sum += Math.min(levelAllowance.get(level), level.getMax());
			}

			// 各社員手当支給額合計を保存
			employeeAllowance.put(employee.getId(), Math.min(sum, 45000));
		}
		
		return employeeAllowance;
	}
	
	/*
	//employee_licenseテーブルを社員番号順にソートするメソッド(20231127追加)
	public List<EmployeeLicense> sortByEmployeeId(){
		return employeeLicenseRepository.findAll(Sort.by(Sort.Direction.ASC, "employee_id"));
	}
	
	//employeeテーブルを社員番号順にソートするメソッド(20231127追加)
	public List<Employee> sortById(){
		return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
	*/
	
}
