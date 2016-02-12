package main;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Compare {

	private ArrayList<QData> qFile;
	private ArrayList<SData> sFile;
	//private ArrayList<IData> iFile;

	public Compare(ArrayList<QData> qFile, ArrayList<SData> sFile, ArrayList<IData> iFile) {
		this.qFile = qFile;
		this.sFile = sFile;
		//this.iFile = iFile;
	}

	void start() {
		qVs();
		sVq();
	}

	private void qVs() {
		
		boolean flag = false;
		Double total = 0.0;
		Double dif = 0.0;
		
		// To check if the student is in both the files SOLUS and QUIC
		// and add the DP0 and DPC cases to QU0 for comparison.
		for(int i=0; i<qFile.size(); i++) {
			flag = false;
			for(int j=0; j<sFile.size(); j++) {
				if((qFile.get(i).getStuNbr()).equalsIgnoreCase(sFile.get(j).getStunbr())) {
					flag = true;
					total = Double.parseDouble(qFile.get(i).getTotalDue());
					for(int k=i+1; k<qFile.size(); k++) {
						if((qFile.get(i).getStuNbr()).equalsIgnoreCase(qFile.get(k).getStuNbr()) && 
								((qFile.get(k).getPrefix()).equalsIgnoreCase("DP0") || (qFile.get(k).getPrefix()).equalsIgnoreCase("DPC"))) {
							
							total += Double.parseDouble(qFile.get(k).getTotalDue());
							i++;
						}
					}
					
					dif = total - Double.parseDouble(sFile.get(j).getTotal());
					DecimalFormat df = new DecimalFormat("#.##");      
					dif = Double.valueOf(df.format(dif));
					
					// Students who QUIC paid more.
					if(dif<0) {
						Output.set(qFile.get(i).getStuNbr(), (i+2), dif);
					}
					// Students who QUIC paid less.
					else if(dif>0) {
						Output.set(qFile.get(i).getStuNbr(), (i+2), dif);
					}
				}
				
			}
			// Students who are not in SOLUS.
			if(!flag) {
				Output.set(qFile.get(i).getStuNbr(), (i+2), qFile.get(i).getTotalDue(), 1);
			}
		}
	}

	private void sVq() {

		boolean flag = false;

		// To check if the student is in both the files SOLUS and QUIC.
		for(int i=0; i<sFile.size(); i++) {
			flag = false;
			for(int j=0; j<qFile.size(); j++) {
				if((sFile.get(i).getStunbr()).equalsIgnoreCase(qFile.get(j).getStuNbr())) {
					flag = true;
				}
			}
			// Students who are not in QUIC.
			if(!flag) {
				Output.set(sFile.get(i).getStunbr(), (i+2), sFile.get(i).getTotal(), 2);
			}
		}
	}
}