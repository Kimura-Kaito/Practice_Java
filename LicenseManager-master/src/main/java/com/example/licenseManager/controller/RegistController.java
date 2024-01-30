package com.example.licenseManager.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.licenseManager.common.StrDate;
import com.example.licenseManager.entity.Employee;
import com.example.licenseManager.entity.EmployeeLicense;
import com.example.licenseManager.entity.License;
import com.example.licenseManager.exception.MyException;
import com.example.licenseManager.form.EmployeeLicenseData;
import com.example.licenseManager.form.EmployeeLicenseDataListParam;
import com.example.licenseManager.repository.EmployeeLicenseRepository;
import com.example.licenseManager.repository.EmployeeRepository;
import com.example.licenseManager.repository.LicenseRepository;
import com.example.licenseManager.service.UploadService;
import com.example.view.EmployeeLicenseExcel;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegistController {

	private final EmployeeRepository employeeRepository;
	private final EmployeeLicenseRepository employeeLicenseRepository;
	private final LicenseRepository licenseRepository;
	private final UploadService uploadService;
	
	// エラーメッセージ生成
	List<String> errorMsgList = new ArrayList<String>();
	List<String> errorMsgList2 = new ArrayList<String>(); //追加(20231128)


	// 資格登録画面を表示
	@GetMapping("/regist")
	public String showRegist(Model model) {

		return "regist";
	}

	@PostMapping("/regist")
	public String uploadEmployeeLicenseExcel(@RequestParam("file_contents") MultipartFile fileContents,
			@RequestParam("sheetNum") int sheetNum, @RequestParam("firstRowNum") int firstRowNum, Model model) {

		// ファイルが空の場合
		if (fileContents.isEmpty()) {

			// エラーメッセージを保存
			errorMsgList.add("ファイルが空です");
			model.addAttribute("errorMsgList", errorMsgList);
			return "regist";
		}

		// 文字列テーブルを生成
		List<List<String>> stringTable = new ArrayList<List<String>>();

		// 列番号に対応するヘッダーを生成
		HashMap<String, Integer> columnIndex = new HashMap<String, Integer>();
		//String[] headers = { "社員番号", "試験名", "取得日", "届出日", "手当金額", "付与開始月", "備考" };
		String[] headers = { "社員番号", "試験名", "取得日", "届出日", "付与開始月", "備考" };

		try {

			// ファイルから文字列テーブルを取得
			stringTable = uploadService.getExcelToStringTable(fileContents, sheetNum, firstRowNum);

			// 列番号を取得
			columnIndex = uploadService.getColumnIndex(stringTable.get(0), headers);

		} catch (MyException e) {

			// エラーメッセージを保存
			errorMsgList.add(e.getMessage());
			model.addAttribute("errorMsgList", errorMsgList);
			return "regist";

		} catch (Exception e) {

			//エラーメッセージ削除(20231129追加)
			if (errorMsgList.size() != 0) {
				errorMsgList.clear();
			}
			// エラーメッセージを保存
			errorMsgList.add("アップロードに失敗しました。選択ファイル間違いがないか確認してください。");
			model.addAttribute("errorMsgList", errorMsgList);
			return "regist";
		}

		// 所持資格データリストを生成
		List<EmployeeLicenseData> employeeLicenseDataList = new ArrayList<>();

		// 行数分繰り返し
		for (int i = 1; i < stringTable.size(); i++) {

			// 文字列行を生成
			List<String> stringRow = stringTable.get(i);

			// 所持資格データを生成
			EmployeeLicenseData employeeLicenseData = new EmployeeLicenseData();

			try {

				// 社員番号を変換
				int employeeId = Integer.parseInt(stringRow.get(columnIndex.get("社員番号")));

				// 社員番号が5桁以上の場合は例外
				if (employeeId > 9999) {
					throw new Exception();
				}

				// 社員を取得
				Employee employee = employeeRepository.findById(employeeId).get();

				// 社員を保存
				employeeLicenseData.setEmployee(employee);

				// 資格を取得
				License license = licenseRepository.findByNameLike("%" + stringRow.get(columnIndex.get("試験名")) + "%")
						.get(0);

				// 資格を保存
				employeeLicenseData.setLicense(license);

				// 変換に失敗
			} catch (Exception e) {

				continue;
			}

			try {

				// 取得日を変換
				String getDate = uploadService.toLocalDateString(stringRow.get(columnIndex.get("取得日")));

				// 取得日を保存
				employeeLicenseData.setGetDate(getDate);

				// 変換に失敗
			} catch (Exception e) {

				employeeLicenseData.setGetDate(null);
			}

			try {

				// 届出日を変換
				String notificationDate = uploadService.toLocalDateString(stringRow.get(columnIndex.get("届出日")));

				// 届出日を保存
				employeeLicenseData.setNotificationDate(notificationDate);

				// 変換に失敗
			} catch (Exception e) {

				employeeLicenseData.setNotificationDate(null);
			}

			// 付与開始月を保存
			//アップロード時にyyyy年しか反映されないエラー発生(20231201)
			employeeLicenseData.setStartDate(stringRow.get(columnIndex.get("付与開始月")));

			// 備考を保存
			employeeLicenseData.setRemarks(stringRow.get(columnIndex.get("備考")));

			// 所持資格データを保存
			employeeLicenseDataList.add(employeeLicenseData);
		}

		// 所持資格データリストを保存
		model.addAttribute("employeeLicenseDataListParam", new EmployeeLicenseDataListParam(employeeLicenseDataList));

		// エラーメッセージを保存
		if (errorMsgList.size() != 0) {
			model.addAttribute("errorMsgList", errorMsgList);
		}

		return "regist";
	}

	// 登録テンプレート（取得資格リスト.xlsx）をダウンロード
	@GetMapping("regist/template")
	public EmployeeLicenseExcel downloadEmployeeLicenseExcelTemplete(EmployeeLicenseExcel excel) {

		// 全所持資格リストを取得して保存
		//List<EmployeeLicense> allEmployeeLicenseList = employeeLicenseRepository.findAll();
		//20231206funayamaソート順を変更
		List<EmployeeLicense> allEmployeeLicenseList = employeeLicenseRepository.queryAll();
		excel.addStaticAttribute("employeeLicenseList", allEmployeeLicenseList);

		//String[] headers = { "社員番号", "氏名", "試験名", "取得日", "届出日", "手当金額", "付与開始月", "備考" };
		String[] headers = { "社員番号", "氏名", "試験名", "取得日", "届出日", "付与開始月", "備考" };
		excel.addStaticAttribute("headers", headers);

		return excel;
	}

	// 取得資格データを追加
	@PostMapping("/regist/add")
	public String addEmployeeLicenseData(@ModelAttribute EmployeeLicenseDataListParam employeeLicenseDataListParam,
			Model model) {

		// 社員資格データリストを取得
		List<EmployeeLicenseData> employeeLicenseDataList = employeeLicenseDataListParam.getEmployeeLicenseDataList();


		// 社員資格データ分繰り返し
		try {
			//20231206funayama変更、テンポラリテーブルは導入前に作成しておく前提で処理を削除とした
			//el_tmp(一時テーブル)の作成
			//employeeLicenseRepository.table(null);
			
			//el_tmp(一時テーブル)のデータ削除処理(20231122)
			employeeLicenseRepository.truncate(null);
			
			for (EmployeeLicenseData employeeLicenseData : employeeLicenseDataList) {
				
				//ストアドプロシージャ呼び出し(el_tmpテーブルにinsert)
				Integer employee = employeeLicenseData.getEmployee().getId();
				Integer license = employeeLicenseData.getLicense().getId();
				Date getDate = StrDate.strdate(employeeLicenseData.getGetDate());
				Date notificationdate = StrDate.strdate(employeeLicenseData.getNotificationDate());
				String startdate = employeeLicenseData.getStartDate();
				String formremarks = employeeLicenseData.getRemarks();
				
				employeeLicenseRepository.insertTmp(employee, license, getDate, notificationdate, startdate, formremarks);
			}
			
			//既存の資格レコードについては更新処理
			employeeLicenseRepository.update_employee_license_exists();
			//新規の資格レコードについては登録処理
			employeeLicenseRepository.insert_employee_license_not_exists();
			//el_tmp(一時テーブル)のデータ削除処理(20231122)
			employeeLicenseRepository.truncate(null);
			
			
		}catch(Exception e) {
			//エラーメッセージを保存(20231116)
			errorMsgList.add(e.getMessage());
			model.addAttribute("errorMsgList", errorMsgList);
			return "regist";
		}

		// 初期画面へリダイレクト
		return "redirect:/top";
	}
	
	// 資格登録画面に戻る
	@PostMapping("regist/cancel")
	public String returnRegist(Model model) {
		//エラーメッセージ削除(20231116追加)
		if (errorMsgList.size() != 0) {
			errorMsgList.clear();
		}
		return "redirect:/regist";
	}
	
	//★↓滝口くんソース
	// フォーム登録画面を表示させる
	//引数追加(20231109)
	@GetMapping("/registForm")
	public String createregistForm(EmployeeLicenseData employeeLicenseData, Model model) {		
		model.addAttribute("employeeLicenseData", employeeLicenseData);
		
		// licenseテーブルからレコードを取得
		List<License> licenseList = licenseRepository.findAll();
		model.addAttribute("licenseList", licenseList);
		
		//全社員リストを取得して保存
		List<Employee> allEmployeeList = employeeRepository.findAll();
		model.addAttribute("allEmployeeList", allEmployeeList);
		
		//社員番号に対応した名前のリスト
		HashMap<Integer, String> employeeNameMap = new HashMap<Integer, String>();
		for(Employee employee : allEmployeeList) {
			employeeNameMap.put(employee.getId(), employee.getName());
		}
		model.addAttribute("employeeNameMap", employeeNameMap);
		
		return "registForm";
	}

	// フォーム登録画面からの情報をもとに資格取得情報を作成
	//入力はフォームで設定された情報であるべき
	//引数追加(20231108)
	@PostMapping("/registForm/create")
	public String createLicenseManager(@Validated @ModelAttribute EmployeeLicenseData employeeLicenseData, BindingResult result, 
			Model model, RedirectAttributes ra, EntityManager entityManager) {		
		// 社員資格取得情報登録
		//employeeLicenseRepository.save(employeeLicenseData.toEntity());
		try {

			//el_tmp(一時テーブル)の作成
			employeeLicenseRepository.table(null);
			
			//el_tmp(一時テーブル)のデータ削除処理(20231122)
			employeeLicenseRepository.truncate(null);
			
			//ストアドファンクション呼び出し(20231113追加)
			Integer employee = employeeLicenseData.getEmployee().getId();
			Integer license = employeeLicenseData.getLicense().getId();
			Date getDate = StrDate.strdate(employeeLicenseData.getGetDate());
			Date notificationdate = StrDate.strdate(employeeLicenseData.getNotificationDate());
			String startdate = employeeLicenseData.getStartDate();
			String formremarks = employeeLicenseData.getRemarks();
			
			employeeLicenseRepository.insertTmp(employee, license, getDate, notificationdate, startdate, formremarks);
		
			// el_tmpから社員資格テーブルに保存(20231115)
			//employeeLicenseRepository.registAll(null);
			employeeLicenseRepository.update_employee_license_exists();
			employeeLicenseRepository.insert_employee_license_not_exists();
			//el_tmp(一時テーブル)のデータ削除処理(20231122)
			employeeLicenseRepository.truncate(null);
			//employeeLicenseRepository.regist(employee, license, getDate, notificationdate, startdate, formremarks);
		}catch(Exception e) {
			//エラーメッセージ削除(20231129追加)
			if (errorMsgList2.size() != 0) {
				errorMsgList2.clear();
			}
			
			errorMsgList2.add("存在しない社員番号が入力されました。");
			//errorMsgList.add(e.getMessage());
			model.addAttribute("errorMsgList2", errorMsgList2);
			
			model.addAttribute("employeeLicenseData", employeeLicenseData);
			
			// licenseテーブルからレコードを取得
			List<License> licenseList = licenseRepository.findAll();
			model.addAttribute("licenseList", licenseList);
			
			//全社員リストを取得して保存
			List<Employee> allEmployeeList = employeeRepository.findAll();
			model.addAttribute("allEmployeeList", allEmployeeList);
			
			//社員番号に対応した名前のリスト
			HashMap<Integer, String> employeeNameMap = new HashMap<Integer, String>();
			for(Employee employee : allEmployeeList) {
				employeeNameMap.put(employee.getId(), employee.getName());
			}
			model.addAttribute("employeeNameMap", employeeNameMap);
			
			return "registForm";
		}
		return "redirect:/top";
	}

	// 資格登録フォーム画面に戻る
	@PostMapping("registForm/cancel")
	public String returnRegistForm(Model model) {

		return "redirect:/registForm";
	}

}
