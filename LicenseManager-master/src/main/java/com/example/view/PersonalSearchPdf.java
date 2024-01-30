package com.example.view;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.example.licenseManager.entity.EmployeeLicense;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PersonalSearchPdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 取得資格リストを取得
		List<EmployeeLicense> employeeLicenseList = (List<EmployeeLicense>) model.get("employeeLicenseList");

		// ファイル名
		String fileName = "取得資格リスト.pdf";
		response.setHeader("Content-Disposition", "inline; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");

		/* フォント */

		// フォント
		Font font = new Font(BaseFont.createFont("HeiseiKakuGo-W5", "UniJIS-UCS2-H", BaseFont.NOT_EMBEDDED), 9,
				Font.NORMAL);

		/* テーブル準備 */

		// 変数
		Phrase phrase;

		// ヘッダー文字列
		//String[] headers = { "社員番号", "氏名", "取得資格", "取得日", "届出日", "手当金額", "付与開始月", "備考" };
		String[] headers = { "社員番号", "氏名", "取得資格", "取得日", "届出日", "付与開始月", "備考" };

		// 列幅
		//int[] tableWidths = { 9, 12, 20, 10, 10, 9, 10, 20 };
		int[] tableWidths = { 9, 12, 20, 10, 10, 10, 20 };

		// テーブル
		Table table = new Table(headers.length);
		table.setWidth(100);
		table.setPadding(3);
		table.setWidths(tableWidths);

		/* 書き込み */

		// ヘッダー
		for (String header : headers) {
			table.addCell(makeCell(header, font, 0.9f, HorizontalAlignment.LEFT));
		}

		// 取得資格分繰り返し
		for (EmployeeLicense employeeLicense : employeeLicenseList) {

			// 社員番号
			phrase = makePhrase(employeeLicense.getEmployee().getId(), font);
			table.addCell(makeCell(phrase, 1, HorizontalAlignment.CENTER));

			// 氏名
			phrase = makePhrase(employeeLicense.getEmployee().getName(), font);
			table.addCell(makeCell(phrase, 1, HorizontalAlignment.LEFT));

			// 取得資格
			phrase = makePhrase(employeeLicense.getLicense().getName(), font);
			table.addCell(makeCell(phrase, 1, HorizontalAlignment.LEFT));

			// 取得日
			phrase = makePhrase(employeeLicense.getGetDate(), font);
			table.addCell(makeCell(phrase, 1, HorizontalAlignment.LEFT));

			// 届出日
			phrase = makePhrase(employeeLicense.getNotificationDate(), font);
			table.addCell(makeCell(phrase, 1, HorizontalAlignment.LEFT));

			/*
			// 手当金額(必要なし)
			phrase = makePhrase(employeeLicense.getLicense().getLevel().getAllowance(), font);
			table.addCell(makeCell(phrase, 1, HorizontalAlignment.RIGHT));
			*/

			// 付与開始月
			phrase = makePhrase(employeeLicense.getStartDate(), font);
			table.addCell(makeCell(phrase, 1, HorizontalAlignment.LEFT));

			// 備考
			phrase = makePhrase(employeeLicense.getRemarks(), font);
			table.addCell(makeCell(phrase, 1, HorizontalAlignment.LEFT));
		}

		doc.add(table);
	}

	/* ヘルパーメソッド */

	private Cell makeCell(String content, Font font, float grayFill, HorizontalAlignment alignment) {

		Cell cell = new Cell(new Phrase(content, font));
		cell.setGrayFill(grayFill);
		cell.setHorizontalAlignment(alignment);

		return cell;
	}

	private Cell makeCell(Phrase phrase, float grayFill, HorizontalAlignment alignment) {

		Cell cell = new Cell(phrase);
		cell.setGrayFill(grayFill);
		cell.setHorizontalAlignment(alignment);

		return cell;
	}

	private Phrase makePhrase(Object obj, Font font) {

		String content;
		if (obj == null) {
			content = "";
		} else {
			content = obj.toString();
		}

		return new Phrase(content, font);
	}
}