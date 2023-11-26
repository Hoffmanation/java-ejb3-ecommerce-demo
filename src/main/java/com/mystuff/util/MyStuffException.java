package com.mystuff.util;

/**
 * Custom exception to handle any exception in the app
 * @author Oren Hoffman
 *
 */
public class MyStuffException extends Exception {
	private static final long serialVersionUID = -5646946067952474915L;

	public MyStuffException(String message) {
	        super(message);
	    }
	
	public MyStuffException(String message, int entityId) {
        super(String.format(message + ", Entity ID: %d", entityId));
    }

	    public MyStuffException(String message, Throwable throwable) {
	        super(message, throwable);
	    }

	}
