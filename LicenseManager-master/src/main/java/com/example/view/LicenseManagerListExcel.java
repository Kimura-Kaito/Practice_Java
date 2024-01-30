package com.example.view;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.example.licenseManager.entity.Employee;
import com.example.licenseManager.entity.EmployeeLicense;
import com.example.licenseManager.entity.Level;
import com.example.licenseManager.entity.License;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LicenseManagerListExcel extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リストを取得
		List<Level> levelList = (List<Level>) model.get("levelList");
		List<Employee> allEmployeeList = (List<Employee>) model.get("allEmployeeList");
		Map<Integer, Boolean> isInvalidLicense = (Map<Integer, Boolean>) model.get("isInvalidLicense");
		Map<Integer, Boolean> haveHigherLicense = (Map<Integer, Boolean>) model.get("haveHigherLicense");
		//追加(20231129)
		Map<Integer, Integer> employeeAllowance = (Map<Integer, Integer>)model.get("employeeAllowance");
		

		// ファイル名
		String fileName = "資格取得管理簿.xlsx";
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");

		/* フォント */

		// タイトルフォント
		Font titleFont = workbook.createFont();
		titleFont.setFontName("游ゴシック");
		titleFont.setFontHeightInPoints((short) 14);
		titleFont.setBold(true);

		// スタンダードフォント
		Font stdFont = workbook.createFont();
		stdFont.setFontName("游ゴシック");
		stdFont.setFontHeightInPoints((short) 11);

		// スタンダードフォント（太字）
		Font stdFontBold = workbook.createFont();
		stdFontBold.setFontName("游ゴシック");
		stdFontBold.setFontHeightInPoints((short) 11);
		stdFontBold.setBold(true);

		/* スタイル */

		// データフォーマット
		DataFormat df = workbook.createDataFormat();

		// タイトルスタイル
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFont(titleFont);

		// ヘッダースタイル
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(stdFont);
		headerStyle.setBorderTop(BorderStyle.MEDIUM);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyle.setWrapText(true);
		headerStyle.setDataFormat(df.getFormat("#,##0"));

		// ヘッダースタイル（右太線）
		CellStyle headerStyleRightMedium = workbook.createCellStyle();
		headerStyleRightMedium.setFont(stdFont);
		headerStyleRightMedium.setBorderTop(BorderStyle.MEDIUM);
		headerStyleRightMedium.setBorderLeft(BorderStyle.THIN);
		headerStyleRightMedium.setBorderRight(BorderStyle.MEDIUM);
		headerStyleRightMedium.setBorderBottom(BorderStyle.MEDIUM);
		headerStyleRightMedium.setAlignment(HorizontalAlignment.CENTER);
		headerStyleRightMedium.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyleRightMedium.setWrapText(true);
		headerStyleRightMedium.setDataFormat(df.getFormat("#,##0"));

		// ヘッダースタイル（太線・太字）
		CellStyle headerStyleMediumBold = workbook.createCellStyle();
		headerStyleMediumBold.setFont(stdFontBold);
		headerStyleMediumBold.setBorderTop(BorderStyle.MEDIUM);
		headerStyleMediumBold.setBorderLeft(BorderStyle.MEDIUM);
		headerStyleMediumBold.setBorderRight(BorderStyle.MEDIUM);
		headerStyleMediumBold.setBorderBottom(BorderStyle.MEDIUM);
		headerStyleMediumBold.setAlignment(HorizontalAlignment.CENTER);
		headerStyleMediumBold.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyleMediumBold.setWrapText(true);
		headerStyleMediumBold.setDataFormat(df.getFormat("#,##0"));

		// 合計行スタイル
		CellStyle sumStyle = workbook.createCellStyle();
		sumStyle.setFont(stdFontBold);
		sumStyle.setBorderTop(BorderStyle.MEDIUM);
		sumStyle.setBorderLeft(BorderStyle.MEDIUM);
		sumStyle.setBorderRight(BorderStyle.MEDIUM);
		sumStyle.setBorderBottom(BorderStyle.MEDIUM);

		// スタンダードスタイル
		CellStyle stdStyle = workbook.createCellStyle();
		stdStyle.setFont(stdFont);
		stdStyle.setBorderTop(BorderStyle.THIN);
		stdStyle.setBorderLeft(BorderStyle.THIN);
		stdStyle.setBorderRight(BorderStyle.THIN);
		stdStyle.setBorderBottom(BorderStyle.THIN);

		// スタンダードスタイル（右太線）
		CellStyle stdStyleRightMedium = workbook.createCellStyle();
		stdStyleRightMedium.setFont(stdFont);
		stdStyleRightMedium.setBorderTop(BorderStyle.THIN);
		stdStyleRightMedium.setBorderLeft(BorderStyle.THIN);
		stdStyleRightMedium.setBorderRight(BorderStyle.MEDIUM);
		stdStyleRightMedium.setBorderBottom(BorderStyle.THIN);
		stdStyleRightMedium.setAlignment(HorizontalAlignment.CENTER);
		stdStyleRightMedium.setDataFormat(df.getFormat("#,##0"));

		// スタンダードスタイル（中央寄せ）
		CellStyle stdStyleCenter = workbook.createCellStyle();
		stdStyleCenter.setFont(stdFont);
		stdStyleCenter.setBorderTop(BorderStyle.THIN);
		stdStyleCenter.setBorderLeft(BorderStyle.THIN);
		stdStyleCenter.setBorderRight(BorderStyle.THIN);
		stdStyleCenter.setBorderBottom(BorderStyle.THIN);
		stdStyleCenter.setAlignment(HorizontalAlignment.CENTER);
		stdStyleCenter.setDataFormat(df.getFormat("#,##0"));

		// 上位資格ありの背景網掛けスタイル(20231124追加)
		CellStyle haveHigherLicenseStyle = workbook.createCellStyle();
		haveHigherLicenseStyle.setFont(stdFont);
		haveHigherLicenseStyle.setBorderTop(BorderStyle.THIN);
		haveHigherLicenseStyle.setBorderLeft(BorderStyle.THIN);
		haveHigherLicenseStyle.setBorderRight(BorderStyle.THIN);
		haveHigherLicenseStyle.setBorderBottom(BorderStyle.THIN);
		haveHigherLicenseStyle.setAlignment(HorizontalAlignment.CENTER);
		haveHigherLicenseStyle.setDataFormat(df.getFormat("#,##0"));
		haveHigherLicenseStyle.setFillPattern(FillPatternType.THIN_FORWARD_DIAG);
		/* シート準備 */

		// 変数
		int y;
		int x;
		int length;
		Row row;
		Cell cell;
		Cell[] cells;

		// 定数
		final int Y0 = 1;
		final int X0 = 1;

		// シート（資格取得管理簿）
		Sheet sheet = workbook.createSheet("資格取得管理簿");
		sheet.setColumnWidth(0, 256 * 4);
		sheet.setColumnWidth(2, 256 * 20);
		sheet.createFreezePane(4, 4);

		/* 書き込み */

		// タイトル（資格取得管理簿）
		y = Y0;
		x = X0;
		row = sheet.createRow(y);
		cell = row.createCell(x);
		cell.setCellValue("資格取得管理簿");
		cell.setCellStyle(titleStyle);

		// ヘッダー1行目
		y++;
		row = sheet.createRow(y);

		// 「社員番号」 下に1行結合
		x = X0;
		cell = row.createCell(x);
		cell.setCellValue("社員番号");
		cell.setCellStyle(headerStyleMediumBold);
		sheet.addMergedRegion(new CellRangeAddress(y, y + 1, x, x));

		// 「氏名」 下に1行結合
		x++;
		cell = row.createCell(x);
		cell.setCellValue("氏名");
		cell.setCellStyle(headerStyleMediumBold);
		sheet.addMergedRegion(new CellRangeAddress(y, y + 1, x, x));

		// 「手当支給額」 下に1行結合
		x++;
		cell = row.createCell(x);
		cell.setCellValue("手当支給額");
		cell.setCellStyle(headerStyleMediumBold);
		sheet.addMergedRegion(new CellRangeAddress(y, y + 1, x, x));

		// レベル分繰り返し
		for (Level level : levelList) {

			// 右に資格分結合
			x++;
			sheet.addMergedRegion(new CellRangeAddress(y, y, x, x + level.getLicenseList().size() - 1));
			x--;

			for (int i = 0; i < level.getLicenseList().size(); i++) {

				// 「レベルi（各xxxx円／上限xxxx円）」
				x++;
				cell = row.createCell(x);
				cell.setCellStyle(headerStyleMediumBold);
				if (i == 0) {
					cell.setCellValue(
							"レベル" + level.getId() + "（各" + level.getAllowance() + "円／上限" + level.getMax() + "円）");
				}
			}
		}

		// ヘッダー2行目
		y++;
		row = sheet.createRow(y);
		row.setHeightInPoints(175);

		// 3回繰り返し
		x = X0 - 1;
		length = 3;
		cells = new Cell[length];
		for (int i = 0; i < length; i++) {

			// （枠線のみ）
			x++;
			cells[i] = row.createCell(x);
			cells[i].setCellStyle(headerStyleMediumBold);
		}

		// 資格分繰り返し
		for (Level level : levelList) {
			for (License license : level.getLicenseList()) {

				// 資格名
				x++;
				cell = row.createCell(x);
				cell.setCellValue(license.getName());
				cell.setCellStyle(headerStyle);
			}

			// 最終列（枠線）
			cell.setCellStyle(headerStyleRightMedium);
		}

		// 全社員分繰り返し
		for (Employee employee : allEmployeeList) {
			//employee.getEmployeeLicenseList().get(employee.getId()).
			
			y++;
			row = sheet.createRow(y);

			// 社員番号
			x = X0;
			cell = row.createCell(x);
			cell.setCellValue(employee.getId());
			cell.setCellStyle(stdStyle);

			// 氏名
			x++;
			cell = row.createCell(x);
			cell.setCellValue(employee.getName());
			cell.setCellStyle(stdStyle);

			// 手当支給額
			x++;
			cell = row.createCell(x);
			//20231206funayama計算しない方式に修正
			cell.setCellValue(employeeAllowance.get(employee.getId()));
			cell.setCellStyle(stdStyle);

			// 資格分繰り返し
			for (Level level : levelList) {
				for (License license : level.getLicenseList()) {

					x++;
					cell = row.createCell(x);

					cell.setCellStyle(stdStyleCenter);
					// 社員の所持資格分繰り返し
					for (EmployeeLicense employeeLicense : employee.getEmployeeLicenseList()) {
						// 所持資格が該当資格と一致した場合
						if (employeeLicense.getLicense().equals(license)) {

							// 資格手当対象外→▲
							if (isInvalidLicense.get(employeeLicense.getId())) {
								//上位資格所持時に網掛け(20231124修正)
								if(haveHigherLicense.get(employeeLicense.getId())) {
									cell.setCellStyle(haveHigherLicenseStyle);
								}
								cell.setCellValue("▲");
	
								// 資格手当対象→〇
							} else {
								//上位資格所持時に網掛け(20231124修正)
								if(haveHigherLicense.get(employeeLicense.getId())) {
									cell.setCellStyle(haveHigherLicenseStyle);
								}
								
								cell.setCellValue("〇");
							}
						}
					}
				}
			}

			// 最終列（枠線）
			cell.setCellStyle(stdStyleRightMedium);
		}

		y++;
		row = sheet.createRow(y);

		// 「合計行」
		cell = row.createCell(X0 + 1);
		cell.setCellValue("合計行");
		cell.setCellStyle(headerStyleMediumBold);

		// 合計行
		cell = row.createCell(X0 + 2);
		
		String xStr = CellReference.convertNumToColString(X0 + 2);
		
		cell.setCellFormula(String.format("SUM(%s5:%s%d)",xStr, xStr,  y - 1));
		cell.setCellStyle(sumStyle);

		for (int i = X0 + 3; i <= x; i++) {

			String iStr = CellReference.convertNumToColString(i);

			cell = row.createCell(i);
			cell.setCellFormula(String.format("COUNTIF(%s5:%s%d,\"〇\")", iStr, iStr, y - 1));
			cell.setCellStyle(sumStyle);
		}
	}
}