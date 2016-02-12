package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadXlsx {
	static XSSFRow row;

	public static XSSFWorkbook openWorkbook(String name) throws QException { 
		File file = new File(name);		
		try{
			FileInputStream fIP = new FileInputStream(file);
			//Get the workbook instance for XLSX file 
			XSSFWorkbook workbook = new XSSFWorkbook(fIP);
			return workbook;

		} catch (IOException e) {
			return null;
		} //handle later



	} // end openWorkbook

	public static ArrayList<QData> getSheetQUIC(XSSFWorkbook workbook) throws QException {
		ArrayList <QData> quic = new ArrayList <QData> (5000);
		int co, rowNum; // counter for columns and rows

		XSSFSheet spreadsheet = workbook.getSheetAt(0);

		Iterator < Row > rowIterator = spreadsheet.iterator();
		row = (XSSFRow) rowIterator.next();

		rowNum = 1;
		while (rowIterator.hasNext()) {
			co = 1;
			QData thisrow = new QData();
			row = (XSSFRow) rowIterator.next();
			Iterator < Cell > cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				if (co == 1)
					thisrow.setPrefix(cell.getStringCellValue());
				else if (co == 2) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						thisrow.setStunbr(Double.toString(cell.getNumericCellValue()));
						break;
					case Cell.CELL_TYPE_STRING:
						thisrow.setStunbr(cell.getStringCellValue());
						break;
					}
				}
				else if (co == 9)
					thisrow.setLastName(cell.getStringCellValue());
				else if (co == 10)
					thisrow.setFirstName(cell.getStringCellValue());
				else if (co == 17) {
					try {
						thisrow.setTotalDue(Double.toString(cell.getNumericCellValue()));
					}
					catch(Exception e) {
						throw new QException("Incorrect total due in ST Master File, student # " + thisrow.getStuNbr() + " , see row " + (rowNum+1));
					}
				}
				co++;
			}
			quic.add(rowNum - 1, thisrow);
			rowNum++;
		}		
		for (int i = quic.size() - 1; i >= 0; i--) {
			if (quic.get(i).getStuNbr() == null || quic.get(i).getPrefix() == null)
				quic.remove(i);
		}
		return quic;
	} // end getSheetQUIC

	public static ArrayList<SData> getSheetSOLUS(XSSFWorkbook workbook) throws QException {
		ArrayList <SData> slist = new ArrayList <SData> (5000);
		int co, rowNum; // counter for columns and rows

		XSSFSheet spreadsheet = workbook.getSheetAt(1);
		Iterator < Row > rowIterator = spreadsheet.iterator();
		row = (XSSFRow) rowIterator.next();

		rowNum = 1;
		while (rowIterator.hasNext()) {
			co = 1;
			SData thisrow = new SData();
			row = (XSSFRow) rowIterator.next();
			Iterator < Cell > cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (co == 1) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						thisrow.setStunbr(Double.toString(cell.getNumericCellValue()));
						break;
					case Cell.CELL_TYPE_STRING:
						thisrow.setStunbr(cell.getStringCellValue());
						break;
					}
				}
				else if (co == 4) {
					try {
						thisrow.setTotal(Double.toString(cell.getNumericCellValue()));
					}
					catch(Exception e) {
						throw new QException("Incorrect total in SOLUS File, student # " + thisrow.getStunbr() + " , see row " + (rowNum+1));
					}
				}

				else if (co == 5) {
					try {
						thisrow.setBalance(Double.toString(cell.getNumericCellValue()));
					}
					catch(Exception e) {
						throw new QException("Incorrect balance in SOLUS File, student # " + thisrow.getStunbr() + " , see row " + (rowNum+1));
					}
				}

				co++;
			}
			slist.add(rowNum - 1, thisrow);
			rowNum++;
		}

		for (int i = slist.size() - 1; i >= 0; i--) {
			if (slist.get(i).getStunbr() == null || slist.get(i).getTotal() == null)
				slist.remove(i);
		}

		return slist;
	} // end getSheetSOLUS
	
	public static ArrayList<IData> getSheetInsurance(XSSFWorkbook workbook) throws QException {
		ArrayList <IData> ilist = new ArrayList <IData> (5000);
		int co, rowNum; // counter for columns and rows

		XSSFSheet spreadsheet = workbook.getSheetAt(2);
		Iterator < Row > rowIterator = spreadsheet.iterator();
		for (int i = 0; i < 5; i++)
			row = (XSSFRow) rowIterator.next();
		rowNum = 1;
		while (rowIterator.hasNext()) {
			co = 1;
			IData thisrow = new IData();
			row = (XSSFRow) rowIterator.next();
			Iterator < Cell > cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (co == 1)
					thisrow.setMemberID(cell.getStringCellValue());
				else if (co == 4) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						thisrow.setPremiumDue(Double.toString(cell.getNumericCellValue()));
						break;
					case Cell.CELL_TYPE_STRING:
						thisrow.setPremiumDue(cell.getStringCellValue());
						break;
					}
				}

				co++;
			}
			ilist.add(rowNum - 1, thisrow);
			rowNum++;
		}

		if (ilist.get(ilist.size() - 1).getMemberID().equals("Totals") || 
				ilist.get(ilist.size() - 1).getMemberID().equals("Total"))
			ilist.remove(ilist.size() - 1);

		return ilist;
	} // end getSheetInsurance
}