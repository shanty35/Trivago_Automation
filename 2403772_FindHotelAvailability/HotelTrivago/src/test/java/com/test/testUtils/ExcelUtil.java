package com.test.testUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	// Static class-level variables
	static String filepath;
	static FileInputStream fis;
	static XSSFWorkbook wb;
	static XSSFSheet sheet;
	static String sheetName;
	static FileOutputStream fos;
	static XSSFRow row;
	static File f;
	static XSSFCell cell;
	static int rows;
	static int col;


	public ExcelUtil(String filepath, String sheetName) {
	    
		ExcelUtil.filepath = filepath;
		f = new File(System.getProperty("user.dir") + "\\" + filepath);
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			wb = new XSSFWorkbook(fis);
			sheet = wb.getSheet(sheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getRowsCount() {
		rows = sheet.getPhysicalNumberOfRows();
		return rows;
	}


	public int getColumnCount() {
		Row row = sheet.getRow(0);
		col = row.getLastCellNum();
		return col;
	}

	
	public String getCellData(int rows, int column) {
		Cell cell = sheet.getRow(rows).getCell(column,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		return cell.toString();
	}

	public static void setCellData(String xlsheet,int rownum,int colnum,String data )throws IOException{
		
		fis=new FileInputStream(f);
		wb=new XSSFWorkbook(fis);
		sheet=wb.getSheet(xlsheet);

		row=sheet.getRow(rownum);
		if (row == null) {
			row = sheet.createRow(rownum);
		}

		row=sheet.getRow(rownum);

		cell=row.createCell(colnum);
		cell.setCellValue(data);

		fos=new FileOutputStream(f);
		wb.write(fos);
	}

}
