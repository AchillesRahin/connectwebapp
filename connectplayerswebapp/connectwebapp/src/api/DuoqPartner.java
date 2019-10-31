package api;

public class DuoqPartner {

	private String partner;
	private int winsWith;
	private int lossesWith;
	private int winsAgainst;
	private int lossesAgainst;
	
	public DuoqPartner(String partner) {
		this.partner = partner;
		this.winsAgainst = 0;
		this.lossesWith = 0;
		this.winsAgainst = 0;
		this.lossesAgainst = 0;
	}
	
	public int getTotal() {
		return this.winsWith + this.winsAgainst + this.lossesAgainst + this.lossesWith;
	}
	
	public void addWinWith() {
		this.winsWith++;
	}
	public void addLossWith() {
		this.lossesWith++;
	}
	
	public void addWinAgainst() {
		this.winsAgainst++;
	}
	public void addLossesAgainst() {
		this.lossesAgainst++;
	}
	
	public String getPartner() {
		return this.partner;
	}

	public int getWinsWith() {
		return winsWith;
	}

	public void setWinsWith(int winsWith) {
		this.winsWith = winsWith;
	}

	public int getLossesWith() {
		return lossesWith;
	}

	public void setLossesWith(int lossesWith) {
		this.lossesWith = lossesWith;
	}

	public int getWinsAgainst() {
		return winsAgainst;
	}

	public void setWinsAgainst(int winsAgainst) {
		this.winsAgainst = winsAgainst;
	}

	public int getLossesAgainst() {
		return lossesAgainst;
	}

	public void setLossesAgainst(int lossesAgainst) {
		this.lossesAgainst = lossesAgainst;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}
	
	
}
