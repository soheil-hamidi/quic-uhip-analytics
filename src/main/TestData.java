package main;
import java.util.ArrayList;

public class TestData {
	
	private ArrayList<QData> qFile;
	private ArrayList<SData> sFile;
	private ArrayList<IData> iFile;

	public TestData(ArrayList<QData> qFile, ArrayList<SData> sFile, ArrayList<IData> iFile) {
		this.qFile = qFile;
		this.sFile = sFile;
		this.iFile = iFile;
	}
	
	void start() throws QException {
		QData studentQ = null;
		SData studentS = null;
		//IData studentI = null;
		String QUIC_file_name = "ST Master File";
		
		for(int i=0; i<qFile.size(); i++) {
			studentQ = qFile.get(i);
			
			checkPrefix(studentQ.getPrefix(), QUIC_file_name, studentQ.getStuNbr(), i);
			checkStNum(studentQ.getStuNbr(), QUIC_file_name, i);
			checkLastName(studentQ.getLastName(), QUIC_file_name, studentQ.getStuNbr(), i);
			checkFirstName(studentQ.getFirstName(), QUIC_file_name, studentQ.getStuNbr(), i);
			checkTotalDue(studentQ.getTotalDue(), QUIC_file_name, studentQ.getStuNbr(), i);
		}
		
		for(int i=0; i<sFile.size(); i++) {
			studentS = sFile.get(i);

			checkStNum(studentS.getStunbr(), "SOLUS" , i);
			checkTotalDue(studentS.getTotal(), "SOLUS", studentS.getStunbr(), i);
			checkTotalDue(studentS.getBalance(), "SOLUS", studentS.getStunbr(), i);
		}
		
		for(int i=0; i<iFile.size(); i++) {
			//studentI = iFile.get(i);
		}
		
	}

	private void checkPrefix(String prefix, String file, String studentNumber, int rowNumber) throws QException {
		if(!(prefix.equalsIgnoreCase("QU0") || prefix.equalsIgnoreCase("DP0") || prefix.equalsIgnoreCase("DPC"))) {
			throw new QException("Incorrect prefix \"" + prefix + "\" in " + file + ", student # " + studentNumber + " , see row " + (rowNumber+2));
		}
	}


	private void checkStNum(String stNum, String file, int rowNumber) throws QException {
		if(!((stNum.length()==8 || stNum.length()==7) && isNumber(stNum))) {
			throw new QException("Incorrect student number in " + file + ", see row " + (rowNumber+2));
		}

	}

	private void checkLastName(String lastName, String file, String studentNumber, int rowNumber) throws QException {
		if(!(isString(lastName))) {
			throw new QException("Incorrect last name in " + file + ", student # " + studentNumber + " , see row " + (rowNumber+2));
		}
	}

	private void checkFirstName(String firstName, String file, String studentNumber, int rowNumber) throws QException {
		if(!(isString(firstName))) {
			throw new QException("Incorrect first name in " + file + ", student # " + studentNumber + " , see row " + (rowNumber+2));
		}
	}

	private void checkTotalDue(String total, String file, String studentNumber, int rowNumber) {
		
		try {
			Double.parseDouble(total);
		} 
		catch(NumberFormatException e) {
			Output.set("Incorrect total due in " + file + ", student # " + studentNumber + " , see row " + (rowNumber+2));
		}
	}

	private boolean isNumber(String s) {

		boolean flag = true;

		for(int i=0; i<s.length(); i++) {
			if(!(Character.isDigit(s.charAt(i)))) {
				flag = false;
			}
		}

		return flag;
	}

	private boolean isString(String s) {

		boolean flag = true;

		for(int i=0; i<s.length(); i++) {
			if(!(Character.isLetter(s.charAt(i)))) {
				flag = false;
			}
		}

		return flag;
	}

}