package com.example.licenseManager.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.example.licenseManager.common.StrDate;
import com.example.licenseManager.exception.MyException;
import com.example.licenseManager.form.EmployeeData;
import com.example.licenseManager.form.EmployeeDataListParam;
import com.example.licenseManager.repository.EmployeeRepository;
import com.example.licenseManager.service.UploadService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MaintenanceController {


	private final EmployeeRepository employeeRepository;
	private final UploadService uploadService;

	// 人事マスタメンテを表示
	@GetMapping("/employeeMaintenance")
	public String showEmployeeMaintenance(Model model) {

		return "employeeMaintenance";
	}

	// Excelファイル（社員情報）をアップロード・プレビューを表示
	@PostMapping("/employeeMaintenance")
	public String uploadEmployeeFile(@RequestParam("file_contents") MultipartFile fileContents,
			@RequestParam("sheetNum") int sheetNum, @RequestParam("firstRowNum") int firstRowNum,
			@RequestParam("pass") String pass, Model model) {

		// エラーメッセージを生成
		List<String> errorMsgList = new ArrayList<String>();

		// ファイルが空の場合
		if (fileContents.isEmpty()) {

			// エラーメッセージを保存
			errorMsgList.add("ファイルが空です");
			model.addAttribute("errorMsgList", errorMsgList);
			return "employeeMaintenance";
		}

		// 文字列テーブルを生成
		List<List<String>> stringTable = new ArrayList<List<String>>();

		// 列番号に対応するヘッダーを生成
		HashMap<String, Integer> columnIndex = new HashMap<String, Integer>();
		String[] headers = { "社員番号", "氏名", "所属部門", "正社員転換日" };

		try {

			// ファイルから文字列テーブルを取得
			stringTable = uploadService.getExcelToStringTable(fileContents, sheetNum, firstRowNum, pass);

			// 列番号を取得
			columnIndex = uploadService.getColumnIndex(stringTable.get(0), headers);

		} catch (MyException e) {

			// エラーメッセージを保存
			errorMsgList.add(e.getMessage());
			model.addAttribute("errorMsgList", errorMsgList);
			return "employeeMaintenance";

		} catch (Exception e) {

			// エラーメッセージを保存
			errorMsgList.add("アップロードに失敗しました。選択ファイル間違いがないか確認してください。");
			model.addAttribute("errorMsgList", errorMsgList);
			return "employeeMaintenance";
		}

		// 社員データリストを生成
		List<EmployeeData> employeeDataList = new ArrayList<>();

		// 行数（ヘッダー除く）分繰り返し
		for (int i = 1; i < stringTable.size(); i++) {

			// 文字列行を生成
			List<String> stringRow = stringTable.get(i);

			// 社員データを生成
			EmployeeData employeeData = new EmployeeData();

			try {

				// 社員番号を変換
				int employeeId = Integer.parseInt(stringRow.get(columnIndex.get("社員番号")));

				// 社員番号が5桁以上の場合は例外
				if (employeeId > 9999) {
					throw new Exception();
				}

				// 社員番号を保存
				employeeData.setId(employeeId);

				// 氏名が空の場合は例外
				if (StringUtils.isEmpty(stringRow.get(columnIndex.get("氏名")))) {
					throw new Exception();
				}

				// 氏名を保存
				employeeData.setName(stringRow.get(columnIndex.get("氏名")));

				// 変換に失敗
			} catch (Exception e) {
				
				continue;
			}

			// 所属部門を保存
			employeeData.setDepartment(stringRow.get(columnIndex.get("所属部門")));

			try {

				// 正社員転換日を変換
				String changeStatusDate = uploadService.toLocalDateString(stringRow.get(columnIndex.get("正社員転換日")));

				// 正社員転換日を保存
				employeeData.setChangeStatusDate(changeStatusDate);

				// 変換に失敗
			} catch (Exception e) {

				// 正社員転換日を空で保存
				employeeData.setChangeStatusDate(null);
			}

			// 社員データを保存
			employeeDataList.add(employeeData);

		}

		// 社員データリストを保存
		model.addAttribute("employeeDataListParam", new EmployeeDataListParam(employeeDataList));

		// エラーメッセージを保存
		if (errorMsgList.size() != 0) {
			model.addAttribute("errorMsgList", errorMsgList);
		}

		return "employeeMaintenance";
	}

	// 社員データを更新
	 @Transactional
	@PostMapping("/employeeMaintenance/update")
	public String updateEmployeeData(@ModelAttribute EmployeeDataListParam employeeDataListParam, Model model) {

		// エラーメッセージ生成20231206funayama追加
		List<String> errorMsgList = new ArrayList<String>();

		// 社員データリストを取得
		List<EmployeeData> employeeDataList = employeeDataListParam.getEmployeeDataList();

		try {
			//20231206funayama追加、テンポラリテーブルの処理は「更新」ボタン処理に集約する一環として
			employeeRepository.truncate_e_tmp();
	
			// 社員データ分繰り返し
			for (EmployeeData employeeData : employeeDataList) {
				
				//ストアドプロシージャ呼び出し(20231117追加)
				Integer id = employeeData.getId();
				String name = employeeData.getName();
				String department = employeeData.getDepartment();
				//20231202funayama変更
				if ( employeeData.getChangeStatusDate() == "") {
					employeeRepository.createtmp(id, name, department, null);
					
				}else {
					Date changeStatusDate = StrDate.strdate(employeeData.getChangeStatusDate());
					//20231206funayama変更
					//テンポラリテーブルへデータ１件登録
					if (changeStatusDate != null) {
						employeeRepository.createtmp(id, name, department, changeStatusDate);
					} else {
						employeeRepository.createtmp(id, name, department, null);
						
					}
				}
			}
	
			//正社員転換時の資格レコード引継ぎ処理（既存資格レコードの社員idを4桁から3桁に変更）
			 employeeRepository.update_employee_license_4_to_3();
			//既存社員の場合は更新処理
			 employeeRepository.update_employee();
			//新入社員の場合は登録処理
			 employeeRepository.insert_employee();
			//退職社員の場合は削除処理
			 employeeRepository.delete_employee_no_emp();
			//退職社員の資格レコード削除処理
			 employeeRepository.delete_employee_license_no_emp();
			 //アップロード分を格納したe_tmpテーブルの全データを削除する
			 employeeRepository.truncate_e_tmp();

		} catch (Exception e) {

			//エラーメッセージ削除(20231129追加)
			if (errorMsgList.size() != 0) {
				errorMsgList.clear();
			}
			// エラーメッセージを保存
			errorMsgList.add(e.getMessage());
			model.addAttribute("errorMsgList", errorMsgList);

			return "employeeMaintenance";
		}
		 
		 
		 
		 
		// 初期画面へリダイレクト
		return "redirect:/top";
	}

	// 人事マスタメンテに戻る
	@PostMapping("/employeeMaintenance/cancel")
	public String returnEmployeeMaintenance(Model model) {

		return "redirect:/employeeMaintenance";
	}
}