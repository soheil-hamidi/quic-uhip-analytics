package main;

public class IData {
	
	private String memberID;
	private String premiumDue;
	
	public IData () {} ;
	
	public IData (String memberID, String premiumDue) {
		this.memberID = memberID;
		this.premiumDue = premiumDue;
	}
	
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	public void setPremiumDue(String premiumDue) {
		this.premiumDue = premiumDue;
	}
	
	public String getMemberID() {
		return this.memberID;
	}
	
	public String getPremiumDue() {
		return this.premiumDue;
	}
}
