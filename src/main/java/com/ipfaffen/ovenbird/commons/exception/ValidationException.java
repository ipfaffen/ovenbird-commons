package com.ipfaffen.ovenbird.commons.exception;

/**
 * @author Isaias Pfaffenseller
 */
@SuppressWarnings("serial")
public class ValidationException extends RuntimeException {

	private String message;
	private Object[] args;
	private String detail;
	private Object object;

	/**
	 * @param message
	 * @param args
	 */
	public ValidationException(String message, Object... args) {
		super(message);
		this.message = message;
		this.args = args;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * @return
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param args
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}
}