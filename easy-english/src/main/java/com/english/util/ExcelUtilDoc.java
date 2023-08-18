package com.english.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.english.entities.QuestionReading;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ExcelUtilDoc {
	
	public List<QuestionReading> getListCauHoiFromFileExcel(InputStream excelFile) {

		try {
			List<QuestionReading> questions = new ArrayList<>();
			Workbook workbook = new XSSFWorkbook(excelFile);

			Sheet datatypeSheet = workbook.getSheetAt(0);

			DataFormatter fmt = new DataFormatter();

			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				QuestionReading questionReading = new QuestionReading();
				questionReading.setNumOrder(fmt.formatCellValue(currentRow.getCell(0)));
				questionReading.setQuestion((fmt.formatCellValue(currentRow.getCell(1))));
				questionReading.setOption1(fmt.formatCellValue(currentRow.getCell(2)));
				questionReading.setOption2(fmt.formatCellValue(currentRow.getCell(3)));
				questionReading.setOption3(fmt.formatCellValue(currentRow.getCell(4)));
				questionReading.setOption4(fmt.formatCellValue(currentRow.getCell(5)));
				questionReading.setCorrectAnswer(fmt.formatCellValue(currentRow.getCell(6)));
				questionReading.setExplanation(fmt.formatCellValue(currentRow.getCell(7)));
				questionReading.setParagraph(fmt.formatCellValue(currentRow.getCell(8)));
				questions.add(questionReading);
			}
			workbook.close();
			return questions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
