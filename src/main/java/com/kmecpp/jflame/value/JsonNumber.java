package com.kmecpp.jflame.value;

import com.kmecpp.jflame.JsonValue;

public class JsonNumber extends JsonValue {

	private final Number value;

	//	public JsonNumber(String value) {
	//		this.value = value;
	//	}

	public JsonNumber(Number value) {
		this.value = value;
		//		this.value = String.valueOf(number);
	}

	public static JsonNumber parse(String str) {
		try {
			return new JsonNumber(Long.parseLong(str));
		} catch (NumberFormatException e) {
			try {
				return new JsonNumber(Double.parseDouble(str));
			} catch (NumberFormatException ex) {
				return null;
			}
		}
	}

	@Override
	public Number get() {
		return value;
	}

	@Override
	public boolean isNumber() {
		return true;
	}

	@Override
	public int asInt() {
		return Integer.parseInt(String.valueOf(value));
	}

	public int toInt() {
		return value.intValue();
	}

	@Override
	public long asLong() {
		return Long.parseLong(String.valueOf(value));
	}

	public long toLong() {
		return value.longValue();
	}

	@Override
	public float asFloat() {
		return Float.parseFloat(String.valueOf(value));
	}

	public float toFloat() {
		return value.floatValue();
	}

	@Override
	public double asDouble() {
		return Double.parseDouble(String.valueOf(value));
	}

	public double toDouble() {
		return value.doubleValue();
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
