package api;

import java.util.List;

public class DuoqPartnerAPIResponse {
	
	public int responseCode;
	public List<DuoqPartner> duoqList;
	String error;
	
	public DuoqPartnerAPIResponse(int code, List<DuoqPartner> list) {
		this.responseCode = code;
		this.duoqList = list;
	}
	
	public DuoqPartnerAPIResponse(int code, String error) {
		this.responseCode = code;
		this.error = error;
	}
	
	public int getResponseCode() {
		return this.responseCode;
	}
	
	public String getError() {
		return this.error;
	}
	
	public List<DuoqPartner> getDuoqList(){
		return this.duoqList;
	}

}
