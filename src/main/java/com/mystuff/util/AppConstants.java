package com.mystuff.util;

/**
 * Application Constants
 * 
 * @author The Hoff
 *
 */
public interface AppConstants {

	// User Messages
	public static final String PASS_LENGTH_NOT_VALID = "*Password Cannot be more than 10 characters";;
	public static final String EMAIL_NOT_VALID = "*Please enter a valid email";
	public static final String FIELD_NOT_VALID = "*{} is required";
	public static final String EMAIL_EXISTS = "email already in use";
	public static final String PASS_DONT_MATCH = "*Looks like the passwords don't match";
	public static final String BAD_LOGIN = "*Invalid password or username, Please try again";
	public static final String BAD_SIGNUP = "*there was a problem with sign up please try again later";
	public static final String GOOD_LOGIN = "You have successfully logged in";
	public static final String GOOD_SIGNUP = "You have successfully signed up";
	public static final String PLEASE_LOG_IN = "Plaese login first !";
	public static final String CART_IS_EMPTY = "Your cart is empty!";
	public static final String NO_CUSTOMER_SESSION = "You're not looged in";
	public static final String UNAUTHORIZED = "You're not looged in";
	public static final String GOOD_LOGOUT = "You are secsussfully loged out";

	// Misc Constants
	public static final String DUMMY_PRODUCTS_FILE = "products.json";
	public static final String UPLOAD_FILE_PATH = "C:/Users/The Hoff/wildfly-10.1.0.Final/standalone/deployments/mystuff.war/images/";
	public static final String CUSTOMER_SESSION_ATTR = "customer_session";
	public static final String SERVER_ERROR = "Server Error! unfortunately, the application cannot process your request at this time.";
	public static final String AUTHORIZATION_PROPERTY = "Authorization";
	public static final String NOT_AUTHORIZED= "You are not authorized to access this resource";
	public static final String AUTHENTICATION_SCHEME = "Basic";

}
