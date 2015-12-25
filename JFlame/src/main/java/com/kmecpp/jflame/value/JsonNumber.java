package com.kmecpp.jflame.value;

import com.kmecpp.jflame.JsonValue;

public class JsonNumber extends JsonValue {

	private final String value;

	public JsonNumber(String value) {
		this.value = value;
	}

	@Override
	public boolean isNumber() {
		return true;
	}

	@Override
	public int asInt() {
		return Integer.parseInt(value, 10);
	}

	@Override
	public long asLong() {
		return Long.parseLong(value, 10);
	}

	@Override
	public float asFloat() {
		return Float.parseFloat(value);
	}

	@Override
	public double asDouble() {
		return Double.parseDouble(value);
	}

	@Override
	public String toString() {
		return value;
	}
}
