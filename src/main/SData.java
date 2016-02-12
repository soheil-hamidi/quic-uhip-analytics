package main;

public class SData {

	private String stunbr; // Student number
	private String total;
	private String balance;
	private String paid;

	public SData () {} ;

	public SData(String stunbr, String total, String balance, String paid) {
		this.stunbr = stunbr;
		this.total = total;
		this.balance = balance;
		this.paid = paid;
	}

	public void setStunbr(String stunbr) {
		if(stunbr.length()<8) {
			this.stunbr = "0" + stunbr;
		}
		else {
			this.stunbr = stunbr;
		}
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getStunbr() {
		return this.stunbr;
	}

	public String getTotal() {
		return this.total;
	}

	public String getBalance() {
		return this.balance;
	}

	public String getPaid() {
		return this.paid;
	}
}
