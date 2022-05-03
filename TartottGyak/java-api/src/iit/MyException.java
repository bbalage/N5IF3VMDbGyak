package iit;

public class MyException extends Exception{
	
	private String msg;
	
	public MyException(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return msg;
	}

}
