package com.kmecpp.jflame;

import com.kmecpp.jflame.value.JsonArray;
import com.kmecpp.jflame.value.JsonNull;
import com.kmecpp.jflame.value.JsonObject;

public abstract class JsonValue {

	public boolean isObject() {
		return false;
	}

	public boolean isArray() {
		return false;
	}

	public boolean isNull() {
		return false;
	}

	public boolean isString() {
		return false;
	}

	public boolean isTrue() {
		return false;
	}

	public boolean isFalse() {
		return false;
	}

	public boolean isNumber() {
		return false;
	}

	public JsonObject asObject() {
		throw new IllegalStateException("Not a JSON object");
	}

	public JsonArray asArray() {
		throw new IllegalStateException("Not a JSON array");
	}

	public JsonNull asNull() {
		throw new IllegalStateException("Not null");
	}

	public boolean asBoolean() {
		throw new IllegalStateException("Not a JSON array");
	}

	public String asString() {
		throw new IllegalStateException("Not a String");
	}

	public int asInt() {
		throw new IllegalStateException("Not an integer");
	}

	public long asLong() {
		throw new IllegalStateException("Not a long");
	}

	public float asFloat() {
		throw new IllegalStateException("Not a float");
	}

	public double asDouble() {
		throw new IllegalStateException("Not a double");
	}

	public boolean isFormattable() {
		return this instanceof IFormattable;
	}

	@Override
	public abstract String toString();
}