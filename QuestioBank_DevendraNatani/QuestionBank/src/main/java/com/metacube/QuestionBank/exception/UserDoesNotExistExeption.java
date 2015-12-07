/**custom exception class for handling user does not exists exception
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.exception;

public class UserDoesNotExistExeption extends Exception {
	
	//error message to be displayed
	private String message;
	
	/**
	 * 
	 * @param message
	 */
	public UserDoesNotExistExeption(String message) {
		this.message = message;
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public UserDoesNotExistExeption(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * 
	 */
	public String getMessage(){
		return message;
	}
}