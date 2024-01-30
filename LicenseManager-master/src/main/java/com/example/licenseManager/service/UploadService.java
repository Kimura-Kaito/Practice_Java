package com.example.licenseManager.service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.licenseManager.exception.MyException;

@Service
public class UploadService {

	// Excelファイルから文字列テーブルを返す（パスワードあり）
	public List<List<String>> getExcelToStringTable(MultipartFile fileContents, int sheetNum, int firstRowNum,
			String pass) throws Exception {

		// ストリームを生成
		InputStream inputStream = fileContents.getInputStream();

		// パスワード解除
		POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);
		EncryptionInfo info = new EncryptionInfo(fileSystem);
		Decryptor d = Decryptor.getInstance(info);

		// パスワードが間違っている場合
		if (d.verifyPassword(pass) == false) {

			// 自作例外をスロー
			throw new MyException("パスワードが違います");
		}

		// ストリームを更新
		inputStream = d.getDataStream(fileSystem);

		// ワークブックを取得
		Workbook workbook = new XSSFWorkbook(inputStream);

		// 文字列テーブルを生成
		List<List<String>> stringTable = getWorkbookToStringTable(workbook, sheetNum, firstRowNum);

		// ワークブックを閉じる
		workbook.close();

		// ストリームを閉じる
		inputStream.close();

		return stringTable;
	}

	// Excelファイルから文字列テーブルを返す（パスワードなし）
	public List<List<String>> getExcelToStringTable(MultipartFile fileContents, int sheetNum, int firstRowNum)
			throws Exception {

		// ストリームを生成
		InputStream inputStream = fileContents.getInputStream();

		// ワークブックを取得
		Workbook workbook = new XSSFWorkbook(inputStream);

		// 文字列テーブルを生成
		List<List<String>> stringTable = getWorkbookToStringTable(workbook, sheetNum, firstRowNum);

		// ワークブックを閉じる
		workbook.close();

		// ストリームを閉じる
		inputStream.close();

		return stringTable;
	}

	// ワークブックから文字列テーブルを返す
	public List<List<String>> getWorkbookToStringTable(Workbook workbook, int sheetNum, int firstRowNum)
			throws Exception {

		// 文字列テーブルを生成
		List<List<String>> stringTable = new ArrayList<List<String>>();

		// シートを取得
		sheetNum = sheetNum - 1;
		Sheet sheet = workbook.getSheetAt(sheetNum);

		// 開始行番号と終了行番号を取得
		firstRowNum = firstRowNum - 1;
		int lastRowNum = sheet.getLastRowNum();

		// 開始行を取得
		Row firstRow = sheet.getRow(firstRowNum);

		// 開始セル番号と終了セル番号を取得
		int firstCellNum = firstRow.getFirstCellNum();
		int lastCellNum = firstRow.getLastCellNum();

		// 文字列行を生成
		List<String> stringRow;

		// 開始行から終了行まで繰り返し
		for (int y = firstRowNum; y <= lastRowNum; y++) {

			// 文字列行を初期化
			stringRow = new ArrayList<String>();

			// 空行の場合は終了
			if (sheet.getRow(y) == null) {
				break;
			}

			// 開始セルから終了セルまで繰り返し
			for (int x = firstCellNum; x <= lastCellNum - 1; x++) {

				// 文字列行に保存
				stringRow.add(getCellValue(sheet.getRow(y).getCell(x), workbook));
			}

			// 文字列テーブルに保存
			stringTable.add(stringRow);
		}

		return stringTable;
	}

	// ヘッダーから特定の列番号を取得する
	public HashMap<String, Integer> getColumnIndex(List<String> stringHeader, String[] headers) throws Exception {

		// 列番号を生成
		HashMap<String, Integer> columnIndex = new HashMap<String, Integer>();

		// ヘッダー行の列分繰り返し
		for (int i = 0; i < stringHeader.size(); i++) {

			// ヘッダー行の列が空でない場合
			if (stringHeader.get(i) != null) {

				// 空白文字・改行文字を削除
				String str = stringHeader.get(i).replaceAll("\s", "").replaceAll("\\r\\n|\\n|\\r", "");

				// ヘッダーが特定の文字列に一致する場合
				if (Arrays.asList(headers).contains(str)) {

					// 特定の列番号を保存
					columnIndex.put(str, i);
				}
			}
		}

		// 列が足りない場合
		if (columnIndex.size() != headers.length) {

			// 自作例外をスロー
			throw new MyException("ファイルが正しくありません");
		}

		return columnIndex;
	}

	// 文字列テーブルから特定の列を抜き出す
	public List<String> extractColumn(List<List<String>> stringTable, int x) {

		// 文字列列を生成
		List<String> stringColumn = new ArrayList<String>();

		// 指定列を追加
		for (int i = 0; i < stringTable.size(); i++) {
			stringColumn.add(stringTable.get(i).get(x));
		}

		return stringColumn;
	}

	// 日付に変換可能な文字列に変換
	public String toLocalDateString(String str) {

		// エポックを生成
		LocalDate epoch = LocalDate.of(1900, 1, 1);

		return epoch.plusDays(Integer.valueOf(str) - 2).toString();
	}

	/* ヘルパーメソッド */

	// セルの値を取得する
	private String getCellValue(Cell cell, Workbook workbook) {

		// セルが存在しない場合
		if (cell == null) {
			return null;
		}

		// セルタイプを判定
		switch (cell.getCellType()) {

		// 文字型
		case STRING:
			return cell.getStringCellValue();

		// 数値型、日付型
		case NUMERIC:
			return String.valueOf((int) cell.getNumericCellValue());

		// 真理値型
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());

		// 関数
		case FORMULA:
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			return String.valueOf((int) evaluator.evaluate(cell).getNumberValue());

		default:
			return null;

		}
	}
}