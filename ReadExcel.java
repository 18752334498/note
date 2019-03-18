package com.excle;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	/**
		<dependency>
			<groupId>org.apache.poi</groupId>
			<artifactId>poi</artifactId>
			<version>3.17</version>
		</dependency>
		<dependency>
			<groupId>org.apache.poi</groupId>
			<artifactId>poi-ooxml</artifactId>
			<version>3.17</version>
		</dependency>
	 * @param args
	 * @throws Exception
	 */

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		String excelPath = "D:\\student.xlsx";

		try {
			File excel = new File(excelPath);
			if (excel.isFile() && excel.exists()) { // 判断文件是否存在

				System.out.println(excel.getName());
				String[] split = excel.getName().split("\\."); // .是特殊字符，需要转义！！！！！

				// 根据文件后缀（xls/xlsx）进行判断
				Workbook wb = null;
				if ("xls".equals(split[1])) {
					FileInputStream fis = new FileInputStream(excel); // 文件流对象
					wb = new HSSFWorkbook(fis);
				} else if ("xlsx".equals(split[1])) {
					wb = new XSSFWorkbook(excel);
				} else {
					System.out.println("文件类型错误!");
					return;
				}

				// 开始解析
				Sheet sheet = wb.getSheetAt(0); // 读取sheet 0
				System.out.println(wb.getAllNames());

				int firstRowIndex = sheet.getFirstRowNum() + 1; // 第一行是列名，所以不读
				int lastRowIndex = sheet.getLastRowNum();
				System.out.println("firstRowIndex: " + firstRowIndex);
				System.out.println("lastRowIndex: " + lastRowIndex);

				for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) { // 遍历行
					Row row = sheet.getRow(rIndex);
					if (row != null) {
						int firstCellIndex = row.getFirstCellNum();
						int lastCellIndex = row.getLastCellNum();
						for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) { // 遍历列
							Cell cell = row.getCell(cIndex);
							if (cell != null) {
								System.out.print(cell + "\t");
							}
						}
						System.out.println();
					}
				}
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}