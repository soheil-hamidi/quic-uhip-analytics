/*
 * To collect all the calculations and processed data
 * and print them in appropriate form.
 */

package main;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Output {

	private static ArrayList<String> stuNum1 = new ArrayList<String>();
	private static ArrayList<String> stuNum2 = new ArrayList<String>();
	private static ArrayList<String> stuNum3 = new ArrayList<String>();
	private static ArrayList<String> stuNum4 = new ArrayList<String>();
	private static ArrayList<Integer> rowNum1 = new ArrayList<Integer>();
	private static ArrayList<Integer> rowNum2 = new ArrayList<Integer>();
	private static ArrayList<Integer> rowNum3 = new ArrayList<Integer>();
	private static ArrayList<Integer> rowNum4 = new ArrayList<Integer>();
	private static ArrayList<Double> amount1 = new ArrayList<Double>();
	private static ArrayList<Double> amount2 = new ArrayList<Double>();
	private static ArrayList<Double> amount3 = new ArrayList<Double>();
	private static ArrayList<Double> amount4 = new ArrayList<Double>();
	private static double total1 = 0, total2 = 0, total3 = 0, total4 =0;
	private static String message= null;

	public Output() {}

	public static void set(String studentNumber, int rowNumber, double amount) {
		if(amount>0) {
			stuNum1.add(studentNumber);
			rowNum1.add(rowNumber);
			amount1.add(amount);
			total1 += amount;

		}
		else {
			stuNum2.add(studentNumber);
			rowNum2.add(rowNumber);
			amount2.add((-amount));
			total2 += (-amount);
		}
	}

	public static void set(String studentNumber, int rowNumber, String amount, int sheetNumber) {

		DecimalFormat df = new DecimalFormat("#.##");
		double payment = Double.valueOf(df.format(Double.parseDouble(amount)));

		if(sheetNumber==1) {
			stuNum3.add(studentNumber);
			rowNum3.add(rowNumber);
			amount3.add(payment);
			total3 += payment;
		}
		else {
			stuNum4.add(studentNumber);
			rowNum4.add(rowNumber);
			amount4.add(payment);
			total4 += payment;
		}
	}

	public static void set(String string) {
		message = string;
	}

	public static void printResult() {

		if(message != null && !message.isEmpty()) {
			System.out.println(message);
		}
		else {
			System.out.println("Students that have higher charges on ST Master than on SOLUS file are:\n");
			for(int i = 0; i<stuNum1.size(); i++) {
				System.out.printf("Student #\t" + stuNum1.get(i) + "\tRow %6d\tDifference of Amounts:\t%7.2f\n", rowNum1.get(i), amount1.get(i));
			}
			System.out.printf("\n\t\t\t\tTOTAL:         %15.2f" + "\n\n", total1);

			System.out.println("Students that have lower charges on ST Master than on SOLUS file are:\n");
			for(int i = 0; i<stuNum2.size(); i++) {
				System.out.printf("Student #\t" + stuNum2.get(i) + "\tRow %6d\tDifference of Amounts:\t%7.2f\n", rowNum2.get(i), amount2.get(i));
			}
			System.out.printf("\n\t\t\t\tTOTAL:         %15.2f" + "\n\n", total2);

			System.out.println("Students that are on ST Master file but not on SOLUS File:\n");
			for(int i = 0; i<stuNum3.size(); i++) {
				System.out.printf("Student #\t" + stuNum3.get(i) + "\tRow %6d\tAmounts:\t%7.2f\n", rowNum3.get(i), amount3.get(i));
			}
			System.out.printf("\n\t\t\tTOTAL:         %15.2f" + "\n\n", total3);

			System.out.println("Students that are on SOLUS File but not on ST Master File:\n");
			for(int i = 0; i<stuNum4.size(); i++) {
				System.out.printf("Student #\t" + stuNum4.get(i) + "\tRow %6d\tAmounts:\t%7.2f\n", rowNum4.get(i), amount4.get(i));
			}
			System.out.printf("\n\t\t\tTOTAL:         %15.2f" + "\n\n", total4);
		}
		stuNum1 = new ArrayList<String>(); stuNum2 = new ArrayList<String>(); stuNum3 = new ArrayList<String>(); stuNum4 = new ArrayList<String>();
		rowNum1 = new ArrayList<Integer>(); rowNum2 = new ArrayList<Integer>(); rowNum3 = new ArrayList<Integer>(); rowNum4 = new ArrayList<Integer>();
		amount1 = new ArrayList<Double>(); amount2 = new ArrayList<Double>(); amount3 = new ArrayList<Double>(); amount4 = new ArrayList<Double>();
		total1 = 0; total2 = 0; total3 = 0; total4 =0;
		message= null;
	}

}
