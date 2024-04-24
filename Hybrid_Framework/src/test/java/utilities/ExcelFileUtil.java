package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {	
	Workbook wb;

	// Constructor for reading Excel path
	public ExcelFileUtil(String ExcelPath) throws Throwable {
		FileInputStream fi = new FileInputStream(ExcelPath);
		wb = WorkbookFactory.create(fi);
	}

	// method for Count No. of rows in Sheet
	public int rowCount(String sheetName) {
		return wb.getSheet(sheetName).getLastRowNum();
	}

	// method for Reading Cell data
	public String getCellData(String SheetName, int row, int column) {
		String data;
		if (wb.getSheet(SheetName).getRow(row).getCell(column).getCellType() == CellType.NUMERIC) {
			int celldata = (int) wb.getSheet(SheetName).getRow(row).getCell(column).getNumericCellValue();
			data = String.valueOf(celldata);
		} else {
			data = wb.getSheet(SheetName).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}

	// method for Creating Cell Data
	public void setCellData(String sheetName, int row, int columns, String status, String WriteExcelpath)
			throws Throwable {
		// get sheet from wb
		Sheet ws = wb.getSheet(sheetName);
		Row rowNum = ws.getRow(row);
		// create cell
		Cell cell = rowNum.createCell(columns);
		// write status
		cell.setCellValue(status);
		if (status.equalsIgnoreCase("PASS")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			// colour with Green
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(columns).setCellStyle(style);
		} else if (status.equalsIgnoreCase("FAIL")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			// colour with Green
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(columns).setCellStyle(style);
		} else if (status.equalsIgnoreCase("BLOCKED")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			// colour with Green
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(columns).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(WriteExcelpath);
		wb.write(fo);
	}

	public static void main(String[] args) throws Throwable {
		// create object for class
		ExcelFileUtil xl = new ExcelFileUtil("D:/Sample.xlsx");
		// count no. fo rows in Emp Sheet
		int rc = xl.rowCount("Emp");
		System.out.println(rc);
		for (int i = 1; i <= rc; i++) {
			// read all rows each cell data
			String fname = xl.getCellData("Emp", i, 0);
			String mname = xl.getCellData("Emp", i, 1);
			String lname = xl.getCellData("Emp", i, 2);
			String eid = xl.getCellData("Emp", i, 3);
			System.out.println(fname + "--" + lname + "--" + mname + "--" + eid);
			xl.setCellData("Emp", i, 4, "FAIL", "D:/ResultOfSample.xlsx");
		}
	}
}
