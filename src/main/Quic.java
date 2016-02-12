/*
 * The program is to read all the data from three different books 
 * QUIC book, SOLUS book and Insurance book from QUIC UHIP
 * spreadsheet test the data and compare the books.
 * @author Soheil Hamidi,
 */
package main;

import gui.Pick;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Quic {

	/**
	 * The main method to start the program.
	 * @param args
	 */
	public static void main(String[] args) {
		Pick.startGui();
	}

	/**
	 * To get the books and start testing and comparing data.
	 * @param fileName - Name of the spreadsheet in string
	 * @throws QException
	 */
	public static void process(String fileName) throws QException {
		XSSFWorkbook file = ReadXlsx.openWorkbook(fileName);
		ArrayList<QData> qlist = ReadXlsx.getSheetQUIC(file);
		ArrayList<SData> slist = ReadXlsx.getSheetSOLUS(file);
		ArrayList<IData> ilist = ReadXlsx.getSheetInsurance(file);

		TestData test = new TestData(qlist, slist, ilist);
		test.start();

		Compare list = new Compare(qlist, slist, ilist);
		list.start();
	}

}