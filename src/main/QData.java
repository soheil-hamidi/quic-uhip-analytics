package main;

public class QData {
	
	private String prefix;
	private String stunbr; // Student Number
	private String lastName;
	private String firstName;
	private String totalDue;
	
	public QData() { };
	
	public QData(String prefix, String stunbr, String lastName, String firstName, String totalDue) {
		this.prefix = prefix;
		this.stunbr = stunbr;
		this.lastName = lastName;
		this.firstName = firstName;
		this.totalDue = totalDue;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public void setStunbr(String stunbr) {
		if(stunbr.length()<8) {
			this.stunbr = "0" + stunbr;
		}
		else {
		this.stunbr = stunbr;
		}
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setTotalDue(String totalDue) {
		this.totalDue = totalDue;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public String getStuNbr() {
		return this.stunbr;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getTotalDue() {
		return this.totalDue;
	}
}
