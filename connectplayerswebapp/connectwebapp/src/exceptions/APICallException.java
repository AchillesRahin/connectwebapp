package exceptions;

public class APICallException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int responseCode;
	
	public APICallException(int res, String e) {
		super(e);
		this.responseCode = res;
	}
	
	public int getResponseCode() {
		return this.responseCode;
	}
}
