package com.ipfaffen.ovenbird.commons;

/**
 * @author Isaias Pfaffenseller
 */
public class NameValue {

	private String name;
	private Object value;
	
	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public static NameValue $(String name, Object value) {
		return new NameValue(name, value);
	}

	/**
	 * @param name
	 * @param value
	 */
	public NameValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}