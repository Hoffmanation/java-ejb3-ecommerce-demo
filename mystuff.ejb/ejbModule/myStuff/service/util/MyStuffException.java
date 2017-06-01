package myStuff.service.util;
public class MyStuffException extends Exception {
	
	
	 public MyStuffException(String message) {
	        super(message);
	    }

	    public MyStuffException(String message, Throwable throwable) {
	        super(message, throwable);
	    }

	}
