package com.example.licenseManager.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.licenseManager.common.ElComparator;
import com.example.licenseManager.entity.Employee;
import com.example.licenseManager.entity.EmployeeLicense;
import com.example.licenseManager.entity.License;
import com.example.licenseManager.repository.EmployeeRepository;
import com.example.licenseManager.repository.LicenseRepository;
import com.example.view.EmployeeLicenseExcel;
import com.example.view.PersonalSearchPdf;
import com.example.view.SelectedSearchPdf;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SearchController {

	private final EmployeeRepository employeeRepository;
	private final LicenseRepository licenseRepository;
	private final HttpSession session;

	// 個人資格出力
	@GetMapping("/personalSearch")
	public String searchWithEmployeeName(Model model) {

		// 全社員リストを取得して保存
		List<Employee> allEmployeeList = employeeRepository.findAll();
		model.addAttribute("allEmployeeList", allEmployeeList);

		return "personalLicense";
	}

	@PostMapping("/personalSearch")
	public String searchWithEmployeeNameResult(@RequestParam("q") String q, RedirectAttributes redirectAttributes,
			Model model) {

		// 社員リストを生成
		List<Employee> employeeList = new ArrayList<Employee>();

		// 入力値が空ではない
		if (!q.isEmpty()) {
			// 入力値から社員リストへ社員を追加
			String[] names = q.split("(,|，|、)+");
			for (String name : names) {
				name = name.strip();
				employeeRepository.findByNameLike("%" + name + "%").forEach(employee -> {
					if (!employeeList.contains(employee)) {
						employeeList.add(employee);
					}
				});
			}
		}

		// 所持資格リストを生成
		List<EmployeeLicense> employeeLicenseList = new ArrayList<EmployeeLicense>();

		// 社員リストから所持資格リストへ所持資格を追加
		for (Employee employee : employeeList) {
			employeeLicenseList.addAll(employee.getEmployeeLicenseList());
		}
		
		//追加した所持資格リストを並び替え
		Collections.sort(employeeLicenseList, new ElComparator());
		
		redirectAttributes.addFlashAttribute("employeeLicenseList", employeeLicenseList);
		redirectAttributes.addFlashAttribute("q", q);
		session.setAttribute("employeeLicenseList", employeeLicenseList);

		return "redirect:/personalSearch";
	}

	// 資格選択出力
	@GetMapping("/selectedSearch")
	public String searchWithLicenseName(Model model) {

		// 全資格リストを取得して保存
		List<License> allLicenseList = licenseRepository.findAll();
		model.addAttribute("allLicenseList", allLicenseList);

		return "selectedLicense";
	}

	// 資格選択出力（検索結果）
	@PostMapping("/selectedSearch")
	public String searchWithLicenseNameResult(@RequestParam("q") String[] q, RedirectAttributes redirectAttributes,
			Model model) {

		// 資格リストを生成
		List<License> licenseList = new ArrayList<License>();

		// 入力値から資格リストへ資格を追加
		for (String name : q) {
			licenseRepository.findByName(name).forEach(license -> {
				licenseList.add(license);
			});
		}

		// 所持資格リストを生成
		List<EmployeeLicense> employeeLicenseList = new ArrayList<EmployeeLicense>();

		// 資格リストから所持資格リストへ所持資格を追加
		for (License license : licenseList) {
			employeeLicenseList.addAll(license.getEmployeeLicenseList());
		}

		// 所持資格リストを保存
		redirectAttributes.addFlashAttribute("employeeLicenseList", employeeLicenseList);
		redirectAttributes.addFlashAttribute("q", q);
		session.setAttribute("employeeLicenseList", employeeLicenseList);

		return "redirect:/selectedSearch";
	}

	//個人資格pdf出力
	@GetMapping("/personalSearch/pdf")
	public PersonalSearchPdf writePersonalSearchPdf(PersonalSearchPdf pdf) {

		// 所持資格リストを取得して保存
		List<EmployeeLicense> employeeLicenseList = (List<EmployeeLicense>) session.getAttribute("employeeLicenseList");
		pdf.addStaticAttribute("employeeLicenseList", employeeLicenseList);

		return pdf;
	}

	//個人資格Excel出力
	@GetMapping("/personalSearch/excel")
	public EmployeeLicenseExcel downloadPersonalSearchExcel(EmployeeLicenseExcel excel) {

		try {
			// 所持資格リストを取得して保存
			List<EmployeeLicense> employeeLicenseList = (List<EmployeeLicense>) session.getAttribute("employeeLicenseList");
			excel.addStaticAttribute("employeeLicenseList", employeeLicenseList);
			
			String[] headers = { "社員番号", "氏名", "試験名", "取得日", "届出日", "付与開始月", "備考" };
			excel.addStaticAttribute("headers", headers);
		}
		catch(Exception ex) {

		}
		return excel;
	}

	//資格取得リストpdf出力
	@GetMapping("/selectedSearch/pdf")
	public SelectedSearchPdf writeSelectedSearchPdf(SelectedSearchPdf pdf) {

		// 所持資格リストを取得して保存
		List<EmployeeLicense> employeeLicenseList = (List<EmployeeLicense>) session.getAttribute("employeeLicenseList");
		pdf.addStaticAttribute("employeeLicenseList", employeeLicenseList);

		return pdf;
	}

	//資格取得リストEvcel出力
	@GetMapping("/selectedSearch/excel")
	public EmployeeLicenseExcel downloadSelectedSearchExcel(EmployeeLicenseExcel excel) {

		// 所持資格リストを取得して保存
		List<EmployeeLicense> employeeLicenseList = (List<EmployeeLicense>) session.getAttribute("employeeLicenseList");
		excel.addStaticAttribute("employeeLicenseList", employeeLicenseList);

		String[] headers = { "試験名", "社員番号", "氏名", "取得日", "届出日", "付与開始月", "備考" };
		excel.addStaticAttribute("headers", headers);

		return excel;
	}
}