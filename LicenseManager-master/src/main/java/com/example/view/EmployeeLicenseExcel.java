package com.example.view;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
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

import com.example.licenseManager.entity.EmployeeLicense;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmployeeLicenseExcel extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 取得資格リストを取得
		List<EmployeeLicense> employeeLicenseList = (List<EmployeeLicense>) model.get("employeeLicenseList");
		
		String[] headers = (String[])model.get("headers");

		// ファイル名
		String fileName = "取得資格リスト.xlsx";
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");

		/* フォント */

		// フォント
		Font font = workbook.createFont();
		font.setFontName("游ゴシック");
		font.setFontHeightInPoints((short) 12);

		// フォント（太字）
		Font fontBold = workbook.createFont();
		fontBold.setFontName("游ゴシック");
		fontBold.setFontHeightInPoints((short) 12);
		fontBold.setBold(true);

		/* スタイル */

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

		// スタンダードスタイル（金額）
		CellStyle stdStyleAmount = workbook.createCellStyle();
		stdStyleAmount.setFont(font);
		stdStyleAmount.setBorderTop(BorderStyle.HAIR);
		stdStyleAmount.setBorderLeft(BorderStyle.HAIR);
		stdStyleAmount.setBorderRight(BorderStyle.HAIR);
		stdStyleAmount.setBorderBottom(BorderStyle.HAIR);
		stdStyleAmount.setDataFormat(df.getFormat("#,##0"));

		// スタンダードスタイル（中央寄せ）
		CellStyle stdStyleCenter = workbook.createCellStyle();
		stdStyleCenter.setFont(font);
		stdStyleCenter.setBorderTop(BorderStyle.HAIR);
		stdStyleCenter.setBorderLeft(BorderStyle.HAIR);
		stdStyleCenter.setBorderRight(BorderStyle.HAIR);
		stdStyleCenter.setBorderBottom(BorderStyle.HAIR);
		stdStyleCenter.setAlignment(HorizontalAlignment.CENTER);

		// スタンダードスタイル
		CellStyle stdStyleDate = workbook.createCellStyle();
		stdStyleDate.setFont(font);
		stdStyleDate.setBorderTop(BorderStyle.HAIR);
		stdStyleDate.setBorderLeft(BorderStyle.HAIR);
		stdStyleDate.setBorderRight(BorderStyle.HAIR);
		stdStyleDate.setBorderBottom(BorderStyle.HAIR);
		CreationHelper createHelper = workbook.getCreationHelper();
		short style = createHelper.createDataFormat().getFormat("yyyy/mm/dd");
		stdStyleDate.setDataFormat(style);

		/* シート準備 */

		// 変数
		int y;
		int x;
		Row row;
		Cell cell;

		// 定数
		final int Y0 = 1;
		final int X0 = 1;

		// シート（選択資格出力）
		Sheet sheet = workbook.createSheet("sheet1");

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
		for (EmployeeLicense employeeLicense : employeeLicenseList) {

			y++;
			row = sheet.createRow(y);

			x = X0 - 1;

			for (String header : headers) {

				x++;
				cell = row.createCell(x);

				switch (header) {

				case "試験名":
					cell.setCellValue(employeeLicense.getLicense().getName());
					cell.setCellStyle(stdStyle);
					break;

				case "社員番号":
					cell.setCellValue(employeeLicense.getEmployee().getId());
					cell.setCellStyle(stdStyleCenter);
					break;

				case "氏名":
					cell.setCellValue(employeeLicense.getEmployee().getName());
					cell.setCellStyle(stdStyle);
					break;

				case "取得日":
					cell.setCellValue(toDate(employeeLicense.getGetDate()));
					cell.setCellStyle(stdStyleDate);
					break;
					
				case "届出日":
					cell.setCellValue(toDate(employeeLicense.getNotificationDate()));
					cell.setCellStyle(stdStyleDate);
					break;
				/*
				 * 必要なし(20231124)
				case "手当金額":
					cell.setCellValue(employeeLicense.getLicense().getLevel().getAllowance());
					cell.setCellStyle(stdStyleAmount);
					break;
				*/
					
				case "付与開始月":
					cell.setCellValue(employeeLicense.getStartDate());
					cell.setCellStyle(stdStyle);
					break;
					
				case "備考":
					cell.setCellValue(employeeLicense.getRemarks());
					cell.setCellStyle(stdStyle);
					break;
				}
			}
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

	private Date toDate(LocalDate localDate) {

		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return simpleDateFormat.parse(localDate.toString());

		} catch (Exception e) {

			return null;
		}
	}
}