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
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.example.licenseManager.entity.Employee;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmployeeMasterExcel extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 社員リストを取得
		List<Employee> allEmployeeList = (List<Employee>) model.get("allEmployeeList");

		// ファイル名
		String fileName = "社員名簿.xlsx";
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");

		/* フォント */

		// フォント
		Font font = workbook.createFont();
		font.setFontName("游ゴシック");
		font.setFontHeightInPoints((short) 11);

		// フォント（太字）
		Font fontBold = workbook.createFont();
		fontBold.setFontName("游ゴシック");
		fontBold.setFontHeightInPoints((short) 11);
		fontBold.setBold(true);

		// データフォーマット
		DataFormat df = workbook.createDataFormat();

		// ヘッダースタイル
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(font);
		headerStyle.setBorderTop(BorderStyle.MEDIUM);
		headerStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerStyle.setBorderRight(BorderStyle.MEDIUM);
		headerStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyle.setFillPattern(FillPatternType.LESS_DOTS);
		headerStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());

		// スタンダードスタイル
		CellStyle stdStyle = workbook.createCellStyle();
		stdStyle.setFont(font);
		stdStyle.setBorderTop(BorderStyle.HAIR);
		stdStyle.setBorderLeft(BorderStyle.HAIR);
		stdStyle.setBorderRight(BorderStyle.HAIR);
		stdStyle.setBorderBottom(BorderStyle.HAIR);

		// スタンダードスタイル（中央寄せ）
		CellStyle stdStyleCenter = workbook.createCellStyle();
		stdStyleCenter.setFont(font);
		stdStyleCenter.setBorderTop(BorderStyle.HAIR);
		stdStyleCenter.setBorderLeft(BorderStyle.HAIR);
		stdStyleCenter.setBorderRight(BorderStyle.HAIR);
		stdStyleCenter.setBorderBottom(BorderStyle.HAIR);
		stdStyleCenter.setAlignment(HorizontalAlignment.CENTER);

		// 変数
		int y;
		int x;
		Row row;
		Cell cell;

		// 定数
		final int Y0 = 1;
		final int X0 = 1;

		// ヘッダー文字列
		String[] headers = { "社員番号", "氏名", "所属部門" };

		// シート（社員名簿）
		Sheet sheet = workbook.createSheet("社員名簿");

		/* 書き込み */

		// ヘッダー行
		y = Y0;
		row = sheet.createRow(y);
		row.setHeightInPoints(sheet.getDefaultRowHeightInPoints() * 1.5f);

		// ヘッダー文字列分繰り返し
		x = X0 - 1;
		for (String header : headers) {

			// ヘッダー
			x++;
			cell = row.createCell(x);
			cell.setCellValue(header);
			cell.setCellStyle(headerStyle);
		}

		// 取得資格分繰り返し
		for (Employee employee : allEmployeeList) {

			y++;
			row = sheet.createRow(y);

			// 社員番号
			x = X0;
			cell = row.createCell(x);
			cell.setCellValue(employee.getId());
			cell.setCellStyle(stdStyleCenter);

			// 氏名
			x++;
			cell = row.createCell(x);
			cell.setCellValue(employee.getName());
			cell.setCellStyle(stdStyle);

			// 所属部門
			x++;
			cell = row.createCell(x);
			cell.setCellValue(employee.getDepartment());
			cell.setCellStyle(stdStyle);
		}

		/* 列幅調整 */

		// 表外
		x = X0 - 1;
		sheet.setColumnWidth(x, 256 * 4);

		// 自動調整
		for (int i = 0; i < headers.length; i++) {
			x++;
			sheet.autoSizeColumn(x);
		}

	}
}