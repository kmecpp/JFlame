package com.kmecpp.jflame;

import com.kmecpp.jflame.value.JsonArray;
import com.kmecpp.jflame.value.JsonBoolean;
import com.kmecpp.jflame.value.JsonNull;
import com.kmecpp.jflame.value.JsonNumber;
import com.kmecpp.jflame.value.JsonObject;
import com.kmecpp.jflame.value.JsonString;

public abstract class JsonValue {

	/**
	 * @return true if the JsonValue is a {@link JsonObject} and false if it's
	 *         not
	 */
	public boolean isObject() {
		return false;
	}

	/**
	 * @return true if the JsonValue is a {@link JsonArray} and false if it's
	 *         not
	 */
	public boolean isArray() {
		return false;
	}

	/**
	 * @return true if the JsonValue is a {@link JsonNull} and false if it's not
	 */
	public boolean isNull() {
		return false;
	}

	/**
	 * @return true if the JsonValue is a {@link JsonString} and false if it's
	 *         not
	 */
	public boolean isString() {
		return false;
	}

	/**
	 * @return true if the JsonValue is a {@link JsonBoolean} and false if it's
	 *         not
	 */
	public boolean isBoolean() {
		return false;
	}

	/**
	 * @return true if the JsonValue is a {@link JsonBoolean} with a value of
	 *         true, and false if it's not
	 */
	public boolean isTrue() {
		return false;
	}

	/**
	 * @return true if the JsonValue is a {@link JsonBoolean} with a value of
	 *         false, and false if it's not
	 */
	public boolean isFalse() {
		return false;
	}

	/**
	 * @return true if the JsonValue is a {@link JsonNumber} and false if it's
	 *         not
	 */
	public boolean isNumber() {
		return false;
	}

	/**
	 * @return the current JsonValue as a {@link JsonObject}
	 */
	public JsonObject asObject() {
		throw new IllegalStateException("Not a JSON object");
	}

	/**
	 * @return the current JsonValue as a {@link JsonArray}
	 */
	public JsonArray asArray() {
		throw new IllegalStateException("Not a JSON array");
	}

	/**
	 * @return the current JsonValue as a {@link JsonNull}
	 */
	public JsonNull asNull() {
		throw new IllegalStateException("Not null");
	}

	/**
	 * @return the current JsonValue as a boolean
	 */
	public boolean asBoolean() {
		throw new IllegalStateException("Not a JSON array");
	}

	/**
	 * @return the current JsonValue as a String
	 */
	public String asString() {
		throw new IllegalStateException("Not a String");
	}

	/**
	 * @return the current JsonValue as an int
	 */
	public int asInt() {
		throw new IllegalStateException("Not an integer");
	}

	/**
	 * @return the current JsonValue as a long
	 */
	public long asLong() {
		throw new IllegalStateException("Not a long");
	}

	/**
	 * @return the current JsonValue as a float
	 */
	public float asFloat() {
		throw new IllegalStateException("Not a float");
	}

	/**
	 * @return the current JsonValue as a double
	 */
	public double asDouble() {
		throw new IllegalStateException("Not a double");
	}

	/**
	 * Gets whether or not the String representation of this JsonValue can be
	 * formatted or not
	 * 
	 * @return true if the JsonValue is formattable and false if it's not
	 */
	public boolean isFormattable() {
		return this instanceof IFormattable;
	}

	@Override
	public abstract String toString();

}
